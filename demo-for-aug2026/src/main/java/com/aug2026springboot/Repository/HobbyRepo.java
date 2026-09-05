package com.aug2026springboot.Repository;

import com.aug2026springboot.model.HobbyModel;
import com.aug2026springboot.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface HobbyRepo extends JpaRepository<HobbyModel,Long>{
//    @Query("select hobby from HobbyRepo hobby where user_id=?1")
//    Optional<HobbyModel> getHobbyFromUserId(Long user_id);
}