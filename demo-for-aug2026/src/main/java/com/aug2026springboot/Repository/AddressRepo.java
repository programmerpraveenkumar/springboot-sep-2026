package com.aug2026springboot.Repository;

import com.aug2026springboot.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepo extends JpaRepository<AddressModel,Long>{

}