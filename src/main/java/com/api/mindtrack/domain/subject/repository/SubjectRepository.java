package com.api.mindtrack.domain.subject.repository;

import com.api.mindtrack.domain.subject.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubjectRepository extends JpaRepository<SubjectModel, Long> {

    List<SubjectModel> findAllByUserId(Long user_id);
}
