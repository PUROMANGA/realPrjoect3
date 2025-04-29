package com.example.minzok.store.service;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.*;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreFactory;
import com.example.minzok.store.entity.StoreStatus;
import com.example.minzok.store.handler.StoreServiceHandler;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor

public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreServiceHandler storeServiceHandler;

    /**
     * 입력받은 memberid로 member을 찾아주고, member와 입력받은 storeRequestDto로 Store를 저장합니다.
     * 그 후 StoreResponseDto로 반환합니다.
     * @param storeRequestDto
     * @param memberId
     * @return
     */

    @Secured({"ROLE_MANAGER"}) //저희는 작업 환경상 ADMIN이 존재하지 않아 MANGAER만 제약을 걸었습니다.
    @Transactional
    @Override
    public StoreMemberDto createStoreService(StoreRequestDto storeRequestDto, String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));

        int storeCount = storeRepository.countByMemberEmail(email);

        if(storeCount >= 3) {
            throw new CustomRuntimeException(ExceptionCode.TOO_MANY_STORES);
        }

        Store store = StoreFactory.storeFactory(storeRequestDto, member);
        member.increaseStoreCount();

        return new StoreMemberDto(storeServiceHandler.changeStoreStatus(store), member);
    }

    /**
     * 입력받은 sotreId의 유무를 확인하고, 있다면 수정해줍니다.
     * @param storeRequestDto
     * @param storeId
     * @param email
     * @return
     */

    @Secured({"ROLE_MANAGER"})
    @Transactional
    @Override
    public StoreResponseDto patchStore(StoreModifyDto storeModifyDto, Long storeId, String email) {
        Store foundStore = storeServiceHandler.foundStoreAndException(storeId, email);
        foundStore.update(storeModifyDto);
        return new StoreResponseDto(storeRepository.save(foundStore));
    }

    /**
     * 입력받은 storeId의 유무를 확인하고, 있다면 삭제합니다.
     * @param storeId
     * @param myUserDetail
     * @return
     */

    @Secured({"ROLE_MANAGER"})
    @Transactional
    @Override
    public void deleteStoreService(Long storeId, String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));
        Store foundStore = storeServiceHandler.foundStoreAndException(storeId, email);
        member.decreaseStoreCount();
        storeServiceHandler.deleteStoreStatus(foundStore);
        storeRepository.save(foundStore);
    }

    /**
     * 특정 KeyWord가 들어간 메뉴의 이름을 가지고 있는 가게를 조회
     * @param keyword
     * @param pageable
     * @return
     */

    @Transactional(readOnly = true)
    @Override
    public Slice<OnlyStoreResponseDto> findStorePage(String keyword, Pageable pageable) {
        Slice<Store> store = storeRepository.storeNameFindByKeyword(keyword, pageable);

        if(store.isEmpty()) {
            throw new CustomRuntimeException(ExceptionCode.NOT_FIND_KEYWORD);
        }
        return store.map(OnlyStoreResponseDto::new);
    }

    /**
     * 특정 가게를 조회하면 모든 메뉴가 같이 나옴
     * @param storeId
     * @return
     */

    @Transactional(readOnly = true)
    @Override
    public Slice<StoreResponseDto> findOneStore(Long storeId, Pageable pageable) {
        Slice<StoreResponseDto> findStore = storeRepository.menuFindById(storeId, pageable);

        if(findStore.isEmpty()) {
            throw new CustomRuntimeException(ExceptionCode.CANT_FIND_STORE);
        }

        return storeRepository.menuFindById(storeId, pageable);
    }
}
