package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

}
