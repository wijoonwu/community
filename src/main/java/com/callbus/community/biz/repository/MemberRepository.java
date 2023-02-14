package com.callbus.community.biz.repository;

import com.callbus.community.biz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccountId(String id);
}
