package com.example.minzok.addresss.repository;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.global.error.CustomNullPointerException;
import com.example.minzok.global.error.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    int countAddressByWithdrawnIsFalseAndMember_Email(String email);
    List<Address> findAddressByMember_Email(String email);

    default Address findAddressByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new CustomNullPointerException(ExceptionCode.CANT_FIND_ADDRESS));
    }



}
