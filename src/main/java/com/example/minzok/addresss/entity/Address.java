package com.example.minzok.addresss.entity;

import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lotNumberAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    protected Address (){}

    private Address(String lotNumberAddress, String detailAddress, AddressType addressType){
        this.lotNumberAddress = lotNumberAddress;
        this.detailAddress = detailAddress;
        this.addressType = addressType;
    }

    private void initMember(Member member){
        this.member = member;
    }

    public static Address of(String lotNumberAddress, String detailAddress, AddressType addressType, Member member ){
        Address address = new Address(lotNumberAddress, detailAddress, addressType);
        address.initMember(member);
        member.getAddresses().add(address);
        return address;
    }

    public String getAddressInfo(){
        return lotNumberAddress + " " + detailAddress;
    }

    public void updateAddressType(AddressType addressType){
        this.addressType = addressType;
    }

}
