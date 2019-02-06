--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

-- Started on 2018-05-02 00:02:01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 16559)
-- Name: news_service; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA news_service;


ALTER SCHEMA news_service OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16637)
-- Name: article_id_seq; Type: SEQUENCE; Schema: news_service; Owner: postgres
--

CREATE SEQUENCE news_service.article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE news_service.article_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 200 (class 1259 OID 16567)
-- Name: article; Type: TABLE; Schema: news_service; Owner: postgres
--

CREATE TABLE news_service.article (
    id bigint DEFAULT nextval('news_service.article_id_seq'::regclass) NOT NULL,
    title character varying(200) NOT NULL,
    brief character varying(500),
    text text,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone DEFAULT now() NOT NULL,
    author_id bigint NOT NULL
);


ALTER TABLE news_service.article OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16577)
-- Name: article_tag; Type: TABLE; Schema: news_service; Owner: postgres
--

CREATE TABLE news_service.article_tag (
    article_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE news_service.article_tag OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16620)
-- Name: comment; Type: TABLE; Schema: news_service; Owner: postgres
--

CREATE TABLE news_service.comment (
    id bigint NOT NULL,
    text text NOT NULL,
    author_id bigint NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone DEFAULT now() NOT NULL,
    article_id bigint NOT NULL
);


ALTER TABLE news_service.comment OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16618)
-- Name: comment_id_seq; Type: SEQUENCE; Schema: news_service; Owner: postgres
--

CREATE SEQUENCE news_service.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE news_service.comment_id_seq OWNER TO postgres;

--
-- TOC entry 2839 (class 0 OID 0)
-- Dependencies: 202
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: news_service; Owner: postgres
--

ALTER SEQUENCE news_service.comment_id_seq OWNED BY news_service.comment.id;


--
-- TOC entry 205 (class 1259 OID 16639)
-- Name: tag_id_seq; Type: SEQUENCE; Schema: news_service; Owner: postgres
--

CREATE SEQUENCE news_service.tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE news_service.tag_id_seq OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16560)
-- Name: tag; Type: TABLE; Schema: news_service; Owner: postgres
--

CREATE TABLE news_service.tag (
    id bigint DEFAULT nextval('news_service.tag_id_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE news_service.tag OWNER TO postgres;

--
-- TOC entry 2696 (class 2604 OID 16623)
-- Name: comment id; Type: DEFAULT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.comment ALTER COLUMN id SET DEFAULT nextval('news_service.comment_id_seq'::regclass);


--
-- TOC entry 2704 (class 2606 OID 16576)
-- Name: article article_pkey; Type: CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- TOC entry 2706 (class 2606 OID 16581)
-- Name: article_tag article_tag_pkey; Type: CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.article_tag
    ADD CONSTRAINT article_tag_pkey PRIMARY KEY (article_id, tag_id);


--
-- TOC entry 2708 (class 2606 OID 16630)
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- TOC entry 2700 (class 2606 OID 16564)
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- TOC entry 2702 (class 2606 OID 16566)
-- Name: tag uq_tag_name; Type: CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.tag
    ADD CONSTRAINT uq_tag_name UNIQUE (name);


--
-- TOC entry 2709 (class 1259 OID 16636)
-- Name: fki_fk_comment_article; Type: INDEX; Schema: news_service; Owner: postgres
--

CREATE INDEX fki_fk_comment_article ON news_service.comment USING btree (article_id);


--
-- TOC entry 2710 (class 2606 OID 16582)
-- Name: article_tag fk_article_tag_article; Type: FK CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.article_tag
    ADD CONSTRAINT fk_article_tag_article FOREIGN KEY (article_id) REFERENCES news_service.article(id);


--
-- TOC entry 2711 (class 2606 OID 16587)
-- Name: article_tag fk_article_tag_tag; Type: FK CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.article_tag
    ADD CONSTRAINT fk_article_tag_tag FOREIGN KEY (tag_id) REFERENCES news_service.tag(id);


--
-- TOC entry 2712 (class 2606 OID 16631)
-- Name: comment fk_comment_article; Type: FK CONSTRAINT; Schema: news_service; Owner: postgres
--

ALTER TABLE ONLY news_service.comment
    ADD CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES news_service.article(id);


-- Completed on 2018-05-02 00:02:02

--
-- PostgreSQL database dump complete
--

