package com.watch.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

import java.util.Collections;


@Service("configService")
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository repo;
 
    public MemberDTO findId(String username) throws Exception {
        return repo.getUserInfoByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDTO member = null;
        try {
            member = repo.getUserInfoByEmail(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getUserEmail())
                .password(member.getUserPw())
                .authorities(Collections.emptyList())
                .build();
    }
}
