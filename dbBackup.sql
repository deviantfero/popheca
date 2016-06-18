--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: register(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: fernando
--

CREATE FUNCTION register(name character varying, lasna character varying, email character varying, pass character varying) RETURNS void
    LANGUAGE sql
    AS $$
	insert into usuario ( nomUsuario, apeUsuario, emailUsuario, passUsuario, cnxUsuario, rol )
	values( name, lasna, email, pass, false, 1 );
$$;


ALTER FUNCTION public.register(name character varying, lasna character varying, email character varying, pass character varying) OWNER TO fernando;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: adminxcliente; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE adminxcliente (
    idadmin integer NOT NULL,
    idcliente integer NOT NULL
);


ALTER TABLE adminxcliente OWNER TO fernando;

--
-- Name: entrada; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE entrada (
    codentrada integer NOT NULL,
    tipoentrada character varying(50) NOT NULL,
    precioentrada double precision NOT NULL
);


ALTER TABLE entrada OWNER TO fernando;

--
-- Name: entrada_codentrada_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE entrada_codentrada_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE entrada_codentrada_seq OWNER TO fernando;

--
-- Name: entrada_codentrada_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE entrada_codentrada_seq OWNED BY entrada.codentrada;


--
-- Name: estado; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE estado (
    idestado character varying(5) NOT NULL,
    nomestado character varying(20)
);


ALTER TABLE estado OWNER TO fernando;

--
-- Name: estadoreserva; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE estadoreserva (
    estadoreserva integer NOT NULL,
    estadoactual character varying(15) NOT NULL
);


ALTER TABLE estadoreserva OWNER TO fernando;

--
-- Name: habitacion; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE habitacion (
    codhabitacion integer NOT NULL,
    maxperson integer DEFAULT 1 NOT NULL,
    prchabitacion double precision NOT NULL,
    dethabitacion character varying(250) DEFAULT 'n/a'::character varying NOT NULL,
    estadoreserva integer NOT NULL,
    idhotel integer NOT NULL
);


ALTER TABLE habitacion OWNER TO fernando;

--
-- Name: habitacion_codhabitacion_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE habitacion_codhabitacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE habitacion_codhabitacion_seq OWNER TO fernando;

--
-- Name: habitacion_codhabitacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE habitacion_codhabitacion_seq OWNED BY habitacion.codhabitacion;


--
-- Name: hotel; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE hotel (
    idhotel integer NOT NULL,
    nomhotel character varying(50) NOT NULL,
    lvlhotel integer DEFAULT 0 NOT NULL,
    descrhotel character varying(300) DEFAULT 'n/a'::character varying NOT NULL,
    idestado character varying(5) NOT NULL
);


ALTER TABLE hotel OWNER TO fernando;

--
-- Name: hotel_idhotel_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE hotel_idhotel_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hotel_idhotel_seq OWNER TO fernando;

--
-- Name: hotel_idhotel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE hotel_idhotel_seq OWNED BY hotel.idhotel;


--
-- Name: plancomida; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE plancomida (
    codplan character varying(10) NOT NULL,
    nomplan character varying(20) NOT NULL,
    descrplan character varying(150) DEFAULT 'n/a'::character varying NOT NULL,
    idhotel integer NOT NULL
);


ALTER TABLE plancomida OWNER TO fernando;

--
-- Name: reserva; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE reserva (
    codreserva integer NOT NULL,
    fechainicior date NOT NULL,
    fechafinr date NOT NULL,
    numadultos integer NOT NULL,
    numninnos integer,
    idfactura integer NOT NULL,
    idusuario integer NOT NULL,
    idestado character varying(5) NOT NULL
);


ALTER TABLE reserva OWNER TO fernando;

--
-- Name: reserva_codreserva_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE reserva_codreserva_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reserva_codreserva_seq OWNER TO fernando;

--
-- Name: reserva_codreserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE reserva_codreserva_seq OWNED BY reserva.codreserva;


--
-- Name: reservaxentrada; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE reservaxentrada (
    codreserva integer NOT NULL,
    codentrada integer NOT NULL,
    cantidad integer NOT NULL,
    fecha date NOT NULL
);


ALTER TABLE reservaxentrada OWNER TO fernando;

--
-- Name: transporte; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE transporte (
    codtransporte character varying(7) NOT NULL,
    modelotransporte character varying(10) NOT NULL,
    numpasajeros integer DEFAULT 1 NOT NULL,
    capalmacenaje double precision NOT NULL,
    estadoreserva integer NOT NULL,
    idhotel integer NOT NULL
);


ALTER TABLE transporte OWNER TO fernando;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE usuario (
    idusuario integer NOT NULL,
    nomusuario character varying(20) NOT NULL,
    apeusuario character varying(20) NOT NULL,
    emailusuario character varying(30) NOT NULL,
    passusuario character varying(100) NOT NULL,
    cnxusuario boolean NOT NULL,
    rol integer NOT NULL
);


ALTER TABLE usuario OWNER TO fernando;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE usuario_idusuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_idusuario_seq OWNER TO fernando;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE usuario_idusuario_seq OWNED BY usuario.idusuario;


--
-- Name: codentrada; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY entrada ALTER COLUMN codentrada SET DEFAULT nextval('entrada_codentrada_seq'::regclass);


--
-- Name: codhabitacion; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY habitacion ALTER COLUMN codhabitacion SET DEFAULT nextval('habitacion_codhabitacion_seq'::regclass);


--
-- Name: idhotel; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY hotel ALTER COLUMN idhotel SET DEFAULT nextval('hotel_idhotel_seq'::regclass);


--
-- Name: codreserva; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva ALTER COLUMN codreserva SET DEFAULT nextval('reserva_codreserva_seq'::regclass);


--
-- Name: idusuario; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY usuario ALTER COLUMN idusuario SET DEFAULT nextval('usuario_idusuario_seq'::regclass);


--
-- Data for Name: adminxcliente; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY adminxcliente (idadmin, idcliente) FROM stdin;
\.


--
-- Data for Name: entrada; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY entrada (codentrada, tipoentrada, precioentrada) FROM stdin;
\.


--
-- Name: entrada_codentrada_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('entrada_codentrada_seq', 1, false);


--
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY estado (idestado, nomestado) FROM stdin;
\.


--
-- Data for Name: estadoreserva; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY estadoreserva (estadoreserva, estadoactual) FROM stdin;
0	Libre
1	Reservado
2	Ocupado
\.


--
-- Data for Name: habitacion; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY habitacion (codhabitacion, maxperson, prchabitacion, dethabitacion, estadoreserva, idhotel) FROM stdin;
\.


--
-- Name: habitacion_codhabitacion_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('habitacion_codhabitacion_seq', 1, false);


--
-- Data for Name: hotel; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY hotel (idhotel, nomhotel, lvlhotel, descrhotel, idestado) FROM stdin;
\.


--
-- Name: hotel_idhotel_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('hotel_idhotel_seq', 1, false);


--
-- Data for Name: plancomida; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY plancomida (codplan, nomplan, descrplan, idhotel) FROM stdin;
\.


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reserva (codreserva, fechainicior, fechafinr, numadultos, numninnos, idfactura, idusuario, idestado) FROM stdin;
\.


--
-- Name: reserva_codreserva_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('reserva_codreserva_seq', 1, false);


--
-- Data for Name: reservaxentrada; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxentrada (codreserva, codentrada, cantidad, fecha) FROM stdin;
\.


--
-- Data for Name: transporte; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY transporte (codtransporte, modelotransporte, numpasajeros, capalmacenaje, estadoreserva, idhotel) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY usuario (idusuario, nomusuario, apeusuario, emailusuario, passusuario, cnxusuario, rol) FROM stdin;
11	juan	vasquez	juan@tumbinbin.com	b58d1cbbf4b0789e57e9fa9b1a06b5ae	f	1
12	Juan	Domingo	britoserious@gmail.com	b58d1cbbf4b0789e57e9fa9b1a06b5ae	f	1
10	Regina	Viscarra	viscarra.regina@gmail.com	cbb4be0cdf250620bc34d885485d6d08	f	1
9	Fernando	Vasquez	fmorataya.04@gmail.com	cbb4be0cdf250620bc34d885485d6d08	f	1
1	root	admin	root@admin.com	63a9f0ea7bb98050796b649e85481845	f	0
\.


--
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('usuario_idusuario_seq', 12, true);


--
-- Name: adminxcliente_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY adminxcliente
    ADD CONSTRAINT adminxcliente_pkey PRIMARY KEY (idadmin, idcliente);


--
-- Name: entrada_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY entrada
    ADD CONSTRAINT entrada_pkey PRIMARY KEY (codentrada);


--
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (idestado);


--
-- Name: estadoreserva_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY estadoreserva
    ADD CONSTRAINT estadoreserva_pkey PRIMARY KEY (estadoreserva);


--
-- Name: habitacion_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY habitacion
    ADD CONSTRAINT habitacion_pkey PRIMARY KEY (codhabitacion);


--
-- Name: hotel_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (idhotel);


--
-- Name: plancomida_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY plancomida
    ADD CONSTRAINT plancomida_pkey PRIMARY KEY (codplan);


--
-- Name: reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (codreserva);


--
-- Name: reservaxentrada_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxentrada
    ADD CONSTRAINT reservaxentrada_pkey PRIMARY KEY (codreserva, codentrada);


--
-- Name: transporte_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY transporte
    ADD CONSTRAINT transporte_pkey PRIMARY KEY (codtransporte);


--
-- Name: usuario_emailusuario_key; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_emailusuario_key UNIQUE (emailusuario);


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- Name: adminxcliente_idadmin_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY adminxcliente
    ADD CONSTRAINT adminxcliente_idadmin_fkey FOREIGN KEY (idadmin) REFERENCES usuario(idusuario) ON DELETE CASCADE;


--
-- Name: adminxcliente_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY adminxcliente
    ADD CONSTRAINT adminxcliente_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES usuario(idusuario) ON DELETE CASCADE;


--
-- Name: habitacion_estadoreserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY habitacion
    ADD CONSTRAINT habitacion_estadoreserva_fkey FOREIGN KEY (estadoreserva) REFERENCES estadoreserva(estadoreserva) ON DELETE CASCADE;


--
-- Name: habitacion_idhotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY habitacion
    ADD CONSTRAINT habitacion_idhotel_fkey FOREIGN KEY (idhotel) REFERENCES hotel(idhotel) ON DELETE CASCADE;


--
-- Name: hotel_idestado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY hotel
    ADD CONSTRAINT hotel_idestado_fkey FOREIGN KEY (idestado) REFERENCES estado(idestado) ON DELETE CASCADE;


--
-- Name: plancomida_idhotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY plancomida
    ADD CONSTRAINT plancomida_idhotel_fkey FOREIGN KEY (idhotel) REFERENCES hotel(idhotel) ON DELETE CASCADE;


--
-- Name: reserva_idestado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idestado_fkey FOREIGN KEY (idestado) REFERENCES estado(idestado) ON DELETE CASCADE;


--
-- Name: reserva_idusuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idusuario_fkey FOREIGN KEY (idusuario) REFERENCES usuario(idusuario) ON DELETE CASCADE;


--
-- Name: reservaxentrada_codentrada_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxentrada
    ADD CONSTRAINT reservaxentrada_codentrada_fkey FOREIGN KEY (codentrada) REFERENCES entrada(codentrada) ON DELETE CASCADE;


--
-- Name: reservaxentrada_codreserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxentrada
    ADD CONSTRAINT reservaxentrada_codreserva_fkey FOREIGN KEY (codreserva) REFERENCES reserva(codreserva) ON DELETE CASCADE;


--
-- Name: transporte_estadoreserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY transporte
    ADD CONSTRAINT transporte_estadoreserva_fkey FOREIGN KEY (estadoreserva) REFERENCES estadoreserva(estadoreserva) ON DELETE CASCADE;


--
-- Name: transporte_idhotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY transporte
    ADD CONSTRAINT transporte_idhotel_fkey FOREIGN KEY (idhotel) REFERENCES hotel(idhotel) ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

