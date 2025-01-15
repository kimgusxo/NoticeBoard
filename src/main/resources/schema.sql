-- member 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    member_id BIGSERIAL PRIMARY KEY,
    id VARCHAR(255),
    password VARCHAR(255)
    );

-- board 테이블 생성
CREATE TABLE IF NOT EXISTS board (
    board_id BIGSERIAL PRIMARY KEY,
    writer VARCHAR(255),
    title VARCHAR(255),
    content TEXT,
    registration_date DATE,
    update_date DATE,
    member_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
    );

-- photo 테이블 생성
CREATE TABLE IF NOT EXISTS photo (
    photo_id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255),
    board_id BIGINT,
    FOREIGN KEY (board_id) REFERENCES board(board_id)
    );

-- reply 테이블 생성
CREATE TABLE IF NOT EXISTS reply (
    reply_id BIGSERIAL PRIMARY KEY,
    writer VARCHAR(255),
    content TEXT,
    registration_date DATE,
    update_date DATE,
    board_id BIGINT,
    FOREIGN KEY (board_id) REFERENCES board(board_id)
    );
