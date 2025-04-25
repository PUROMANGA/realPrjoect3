package com.example.minzok.member.service;


import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.member.repository.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private MemberService memberService;



}
