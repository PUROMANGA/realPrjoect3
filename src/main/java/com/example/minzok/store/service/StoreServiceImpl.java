package com.example.minzok.store.service;

import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.store.dto.StoreRequestDto;
import com.example.minzok.store.dto.StoreResponseDto;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreFactory;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.minzok.global.auth.MyUserDetail;

@Service
@Slf4j
@RequiredArgsConstructor

public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

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

    @Override
    public StoreResponseDto patchStore(StoreRequestDto storeRequestDto, Long storeId, String email) {
        storeRepository.findById(storeId).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_STORE));
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));

        if(!member.getEmail().equals(email)) {
            throw new CustomRuntimeException(ExceptionCode.NO_EDIT_PERMISSION);
        }

        Store updated = StoreFactory.update(storeRequestDto);
        return new StoreResponseDto(storeRepository.save(updated));
    }
}
