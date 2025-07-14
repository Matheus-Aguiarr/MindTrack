package com.api.mindtrack.domain.subject.repository;

import com.api.mindtrack.domain.subject.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubjectRepository extends JpaRepository<SubjectModel, Long> {

    @Query("SELECT s FROM SubjectModel s WHERE s.user.id = :user_id ORDER BY s.name ASC")
    List<SubjectModel> findAllByUserId(Long user_id);
}
