package com.watch.project.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.repository.admin.AdminMemberRepository;

@Service
public class MemberService {
@Autowired private AdminMemberRepository repo;
}
