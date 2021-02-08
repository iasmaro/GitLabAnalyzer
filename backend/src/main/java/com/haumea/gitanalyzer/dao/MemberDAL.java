package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Member;

import java.util.List;

public interface MemberDAL {
    List<Member> getAllMembers();
    Member addNewMember(Member member);
}
