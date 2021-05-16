package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Vote;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    boolean existsByUserIdAndVotingDate(Integer user_id, LocalDate votingDate);

    @Cacheable("Votes By Date")
    List<Vote> findAllByVotingDate(LocalDate votingDate);

    Vote findByUserIdAndVotingDate(Integer user_id, LocalDate votingDate);
}