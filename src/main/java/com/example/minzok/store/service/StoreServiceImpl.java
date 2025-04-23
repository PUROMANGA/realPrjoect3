package com.example.minzok.store.service;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.store.dto.StoreMenuDto;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreFactory;
import com.example.minzok.store.handler.StoreServiceHandler;
import com.example.minzok.store.repository.CustomStoreRepository;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor

public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreServiceHandler storeServiceHandler;
    private final MenuRepository menuRepository;

    /**
     * 입력받은 memberid로 member을 찾아주고, member와 입력받은 storeRequestDto로 Store를 저장합니다.
     * 그 후 StoreResponseDto로 반환합니다.
     * @param storeRequestDto
     * @param memberId
     * @return
     */

    @Transactional
    @Override
    public StoreResponseDto createStoreService(StoreRequestDto storeRequestDto, String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));
        Store store = StoreFactory.storeFactory(storeRequestDto, member);
        return new StoreResponseDto(store);
    }

    /**
     * 입력받은 sotreId의 유무를 확인하고, 있다면 수정해줍니다.
     * @param storeRequestDto
     * @param storeId
     * @param email
     * @return
     */

    @Transactional
    @Override
    public StoreResponseDto patchStore(StoreRequestDto storeRequestDto, Long storeId, String email) {
        Store foundStore = storeServiceHandler.foundStoreAndException(storeId, email);
        foundStore.update(storeRequestDto);
        return new StoreResponseDto(storeRepository.save(foundStore));
    }

    /**
     * 입력받은 storeId의 유무를 확인하고, 있다면 삭제합니다.
     * @param storeId
     * @param myUserDetail
     * @return
     */

    @Transactional
    @Override
    public void deleteStoreService(Long storeId, String email) {
        Store foundStore = storeServiceHandler.foundStoreAndException(storeId, email);
        storeRepository.delete(foundStore);
    }

    /**
     * 특정 KeyWord가 들어간 메뉴의 이름을 전체조회
     * @param keyword
     * @param pageable
     * @return
     */

    @Transactional(readOnly = true)
    @Override
    public Slice<StoreResponseDto> findStorePage(String keyword, Pageable pageable) {
        Slice<Store> store = storeRepository.storeNameFindByKeyword(keyword, pageable);
        return store.map(StoreResponseDto::new);
    }

    @Override
    public Slice<StoreResponseDto> findOneStore(Long storeId, Pageable pageable) {
        Slice<StoreResponseDto> findStore = storeRepository.menuFindById(storeId, pageable);
        return findStore;
    }
}
