package com.skafenko.core.repository;

import com.skafenko.core.model.Vote;

import java.util.List;

public interface VoteRepository {
    List<Vote> findByUserId(String userId);

    List<Vote> findByLinkId(String linkId);

    Vote saveVote(Vote vote);
}
