package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    boolean existsByUserIdAndVotingDate(Integer user_id, LocalDate votingDate);
    List<Vote> findAllByVotingDate(LocalDate votingDate);
    Vote findByUserIdAndVotingDate(Integer user_id, LocalDate votingDate);

}
