package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @DisplayName("회원가입을 하고 id로 조회를 하면 동일한 객체를 불러온다.")
    @Test
    void 회원가입(){
        //given
        Member member = new Member();
        member.setName("창수");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findById(saveId).get());
    }

    @DisplayName("회원가입 시 중복이 발생하면 IllegalStateException 이 발생하고, 에러메시지가 동일한지 검증한다.")
    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("창수");

        Member member2 = new Member();
        member2.setName("창수");

        //when
        memberService.join(member1);
        IllegalStateException thrown =assertThrows(IllegalStateException.class, () ->
            memberService.join(member2));

        //then
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

    }

}