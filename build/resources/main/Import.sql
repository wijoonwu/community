insert into MEMBER (account_id , account_type, nickname, quit) values ('Realtor 1' ,'Realtor', '김양', 0);
insert into ARTICLE (member_id, title, content, thumbs_ups, created, modified) values (1, '첫 게시글 입니다!', '만나서 반갑습니다', 0, now(), now());
insert into ARTICLE (member_id, title, content, thumbs_ups, created, modified) values (1, '두번째 게시글 입니다!', '잘 부탁드립니다.', 0, now(), now());