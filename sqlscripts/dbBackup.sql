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
    idestado integer NOT NULL,
    nomestado character varying(20)
);


ALTER TABLE estado OWNER TO fernando;

--
-- Name: estado_idestado_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE estado_idestado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE estado_idestado_seq OWNER TO fernando;

--
-- Name: estado_idestado_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE estado_idestado_seq OWNED BY estado.idestado;


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
    idestado integer NOT NULL,
    engdescrhotel character varying(300)
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
    codplan integer NOT NULL,
    nomplan character varying(20) NOT NULL,
    descrplan character varying(150) DEFAULT 'n/a'::character varying NOT NULL,
    idhotel integer NOT NULL
);


ALTER TABLE plancomida OWNER TO fernando;

--
-- Name: plancomida_codplan_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE plancomida_codplan_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE plancomida_codplan_seq OWNER TO fernando;

--
-- Name: plancomida_codplan_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE plancomida_codplan_seq OWNED BY plancomida.codplan;


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
    idestado integer NOT NULL,
    codtransporte character varying(7),
    idhotel integer NOT NULL
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
-- Name: reservaxhabitacion; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE reservaxhabitacion (
    codreserva integer NOT NULL,
    codhabitacion integer NOT NULL,
    canttipo integer DEFAULT 1
);


ALTER TABLE reservaxhabitacion OWNER TO fernando;

--
-- Name: reservaxplan; Type: TABLE; Schema: public; Owner: fernando
--

CREATE TABLE reservaxplan (
    codreserva integer NOT NULL,
    codplan integer NOT NULL,
    cantidad integer DEFAULT 1
);


ALTER TABLE reservaxplan OWNER TO fernando;

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
-- Name: idestado; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY estado ALTER COLUMN idestado SET DEFAULT nextval('estado_idestado_seq'::regclass);


--
-- Name: codhabitacion; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY habitacion ALTER COLUMN codhabitacion SET DEFAULT nextval('habitacion_codhabitacion_seq'::regclass);


--
-- Name: idhotel; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY hotel ALTER COLUMN idhotel SET DEFAULT nextval('hotel_idhotel_seq'::regclass);


--
-- Name: codplan; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY plancomida ALTER COLUMN codplan SET DEFAULT nextval('plancomida_codplan_seq'::regclass);


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
1	Florida
2	Washington
3	California
4	Maryland
5	Kansas
6	New York
7	New Mexico
8	California
9	Arizona
10	California
11	West Virginia
12	Florida
13	Virginia
14	Nevada
15	North Carolina
16	New York
17	Wisconsin
18	Missouri
19	New Jersey
20	North Carolina
21	Illinois
22	Texas
23	Texas
24	North Carolina
25	Florida
26	Texas
27	Louisiana
28	Texas
29	Arizona
30	Ohio
31	New York
32	Texas
33	Kansas
34	Oklahoma
35	Colorado
36	Tennessee
37	Oklahoma
38	California
39	New York
40	Texas
41	North Carolina
42	Florida
43	California
44	Georgia
45	Florida
46	Michigan
47	New York
48	Texas
49	Missouri
50	Texas
\.


--
-- Name: estado_idestado_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('estado_idestado_seq', 50, true);


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

COPY hotel (idhotel, nomhotel, lvlhotel, descrhotel, idestado, engdescrhotel) FROM stdin;
1	Library Hotel	4	Ubicado en el noreste de Estados Unidos con aproximadamente 8,4 millones de habitantes, dentro de este estado se encuentran algunos de los monumentos y edificios mas reconocidos al rededor del mundo, bordeado por el rio Hudson que forma un puerto natural y que desemboca en el oceano Atlantico.	1	n/a
2	Liberty Hotel	3	Ubicado en el noreste de Estados Unidos con aproximadamente 8,4 millones de habitantes, dentro de este estado se encuentran algunos de los monumentos y edificios mas reconocidos al rededor del mundo, bordeado por el rio Hudson que forma un puerto natural y que desemboca en el oceano Atlantico.	1	n/a
3	Pokemon Hotel	3	Ubicado en el noreste de Estados Unidos con aproximadamente.	1	n/a
4	Digimon Hotel	5	Ubicado en el noreste de Eos Unidos con aproximadamente.	1	n/a
5	Transilvania Hotel	5	Ubicado en el noreste de Eos Unidos con aproximadamente.	1	n/a
\.


--
-- Name: hotel_idhotel_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('hotel_idhotel_seq', 5, true);


--
-- Data for Name: plancomida; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY plancomida (codplan, nomplan, descrplan, idhotel) FROM stdin;
\.


--
-- Name: plancomida_codplan_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('plancomida_codplan_seq', 1, false);


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reserva (codreserva, fechainicior, fechafinr, numadultos, numninnos, idfactura, idusuario, idestado, codtransporte, idhotel) FROM stdin;
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
-- Data for Name: reservaxhabitacion; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxhabitacion (codreserva, codhabitacion, canttipo) FROM stdin;
\.


--
-- Data for Name: reservaxplan; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxplan (codreserva, codplan, cantidad) FROM stdin;
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
1	root	admin	root@admin.com	63a9f0ea7bb98050796b649e85481845	f	0
2	Fernando	Vasquez	fmorataya.04@gmail.com	cbb4be0cdf250620bc34d885485d6d08	f	1
\.


--
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('usuario_idusuario_seq', 2, true);


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
-- Name: reservaxhabitacion_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxhabitacion
    ADD CONSTRAINT reservaxhabitacion_pkey PRIMARY KEY (codreserva, codhabitacion);


--
-- Name: reservaxplan_pkey; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxplan
    ADD CONSTRAINT reservaxplan_pkey PRIMARY KEY (codreserva, codplan);


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
-- Name: reserva_codtransporte_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_codtransporte_fkey FOREIGN KEY (codtransporte) REFERENCES transporte(codtransporte) ON DELETE CASCADE;


--
-- Name: reserva_idestado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idestado_fkey FOREIGN KEY (idestado) REFERENCES estado(idestado) ON DELETE CASCADE;


--
-- Name: reserva_idhotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idhotel_fkey FOREIGN KEY (idhotel) REFERENCES hotel(idhotel) ON DELETE CASCADE;


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
-- Name: reservaxhabitacion_codhabitacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxhabitacion
    ADD CONSTRAINT reservaxhabitacion_codhabitacion_fkey FOREIGN KEY (codhabitacion) REFERENCES habitacion(codhabitacion) ON DELETE CASCADE;


--
-- Name: reservaxhabitacion_codreserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxhabitacion
    ADD CONSTRAINT reservaxhabitacion_codreserva_fkey FOREIGN KEY (codreserva) REFERENCES reserva(codreserva) ON DELETE CASCADE;


--
-- Name: reservaxplan_codplan_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxplan
    ADD CONSTRAINT reservaxplan_codplan_fkey FOREIGN KEY (codplan) REFERENCES plancomida(codplan) ON DELETE CASCADE;


--
-- Name: reservaxplan_codreserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY reservaxplan
    ADD CONSTRAINT reservaxplan_codreserva_fkey FOREIGN KEY (codreserva) REFERENCES reserva(codreserva) ON DELETE CASCADE;


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

