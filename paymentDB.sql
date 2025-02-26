--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    id integer NOT NULL,
    card_number character varying(255),
    card_tipe character varying(255),
    client_id integer
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.account_id_seq OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- Name: invoices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoices (
    invoice_id integer NOT NULL,
    invoice_date timestamp(6) without time zone,
    invoice_method_pay character varying(255),
    invoice_total_purchase_value double precision,
    client_id integer
);


ALTER TABLE public.invoices OWNER TO postgres;

--
-- Name: invoices_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.invoices_invoice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.invoices_invoice_id_seq OWNER TO postgres;

--
-- Name: invoices_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.invoices_invoice_id_seq OWNED BY public.invoices.invoice_id;


--
-- Name: persistence_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persistence_product (
    id_product integer NOT NULL,
    name_product character varying(255),
    price_product double precision,
    quantity_product integer
);


ALTER TABLE public.persistence_product OWNER TO postgres;

--
-- Name: persistence_product_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persistence_product_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.persistence_product_seq OWNER TO postgres;

--
-- Name: persistence_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persistence_user (
    client_ci integer NOT NULL,
    client_email character varying(255),
    client_name character varying(255),
    client_phone character varying(255)
);


ALTER TABLE public.persistence_user OWNER TO postgres;

--
-- Name: account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- Name: invoices invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoices ALTER COLUMN invoice_id SET DEFAULT nextval('public.invoices_invoice_id_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account (id, card_number, card_tipe, client_id) FROM stdin;
1	23748492	Pichincha	1723647178
\.


--
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.invoices (invoice_id, invoice_date, invoice_method_pay, invoice_total_purchase_value, client_id) FROM stdin;
\.


--
-- Data for Name: persistence_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persistence_product (id_product, name_product, price_product, quantity_product) FROM stdin;
1	Galletas	1	3
2	Doritos	0.5	29
\.


--
-- Data for Name: persistence_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persistence_user (client_ci, client_email, client_name, client_phone) FROM stdin;
1723647178	example2@email.com	Jose	03449572834
\.


--
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_id_seq', 33, true);


--
-- Name: invoices_invoice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.invoices_invoice_id_seq', 1, false);


--
-- Name: persistence_product_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persistence_product_seq', 1601, true);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- Name: invoices invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id);


--
-- Name: persistence_product persistence_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persistence_product
    ADD CONSTRAINT persistence_product_pkey PRIMARY KEY (id_product);


--
-- Name: persistence_user persistence_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persistence_user
    ADD CONSTRAINT persistence_user_pkey PRIMARY KEY (client_ci);


--
-- Name: account fk12885nync08mbkuxigjmnbcqc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fk12885nync08mbkuxigjmnbcqc FOREIGN KEY (client_id) REFERENCES public.persistence_user(client_ci);


--
-- Name: invoices fk5lrcylra3u3trvqbhaj6cpous; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT fk5lrcylra3u3trvqbhaj6cpous FOREIGN KEY (client_id) REFERENCES public.persistence_user(client_ci);


--
-- PostgreSQL database dump complete
--

