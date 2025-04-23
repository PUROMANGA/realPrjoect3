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
    public StoreResponseDto createStoreService(StoreRequestDto storeRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));
        Store store = StoreFactory.storeFactory(storeRequestDto, member);
        return new StoreResponseDto(store);
    }

    @Override
    public StoreResponseDto patchStore(StoreRequestDto storeRequestDto, Long StoreId, Long memberId) {
        Store store = storeRepository.findById(StoreId).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_STORE)); // 테스트 예정
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_MEMBER));

        if(!member.getId().equals(memberId)) {
            throw new CustomRuntimeException(ExceptionCode.NO_EDIT_PERMISSION);
        }

        StoreFactory.update(storeRequestDto);
    }
}
