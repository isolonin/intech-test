--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

-- Started on 2016-10-13 01:46:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2118 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 42536)
-- Name: melodies; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE melodies (
    id integer NOT NULL,
    user_id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE melodies OWNER TO test;

--
-- TOC entry 183 (class 1259 OID 42548)
-- Name: seq_melodies; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE seq_melodies
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_melodies OWNER TO test;

--
-- TOC entry 181 (class 1259 OID 42529)
-- Name: users; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE users (
    id integer NOT NULL,
    msisdn character varying(11) NOT NULL
);


ALTER TABLE users OWNER TO test;

--
-- TOC entry 2109 (class 0 OID 42536)
-- Dependencies: 182
-- Data for Name: melodies; Type: TABLE DATA; Schema: public; Owner: test
--

COPY melodies (id, user_id, name) FROM stdin;
\.


--
-- TOC entry 2119 (class 0 OID 0)
-- Dependencies: 183
-- Name: seq_melodies; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('seq_melodies', 24, true);


--
-- TOC entry 2108 (class 0 OID 42529)
-- Dependencies: 181
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: test
--

COPY users (id, msisdn) FROM stdin;
1	79202599619
\.


--
-- TOC entry 1990 (class 2606 OID 42540)
-- Name: melodies_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY melodies
    ADD CONSTRAINT melodies_pkey PRIMARY KEY (id);


--
-- TOC entry 1992 (class 2606 OID 42542)
-- Name: melodies_user_id_name_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY melodies
    ADD CONSTRAINT melodies_user_id_name_key UNIQUE (user_id, name);


--
-- TOC entry 1986 (class 2606 OID 42535)
-- Name: users_msisdn_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_msisdn_key UNIQUE (msisdn);


--
-- TOC entry 1988 (class 2606 OID 42533)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 1993 (class 2606 OID 42543)
-- Name: melodies_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY melodies
    ADD CONSTRAINT melodies_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-10-13 01:46:52

--
-- PostgreSQL database dump complete
--

