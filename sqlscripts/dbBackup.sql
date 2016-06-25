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
-- Name: getroom_reserve(integer); Type: FUNCTION; Schema: public; Owner: fernando
--

CREATE FUNCTION getroom_reserve(numhabitacion integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
declare
	ref refcursor;
begin
	open ref for select X.codhabitacion, X.codreserva, R.fechainicior,
	R.fechafinr from reservaxhabitacion as X, reserva as R
	where X.codreserva=R.codreserva and X.codhabitacion=numhabitacion;
	return ref;
end;
$$;


ALTER FUNCTION public.getroom_reserve(numhabitacion integer) OWNER TO fernando;

--
-- Name: makereserve(date, date, integer, integer, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: fernando
--

CREATE FUNCTION makereserve(fechaini date, fechafin date, numa integer, numn integer, idfac integer, idu integer, ide integer, idh integer) RETURNS void
    LANGUAGE sql
    AS $$
	insert into reserva (fechainicior, fechafinr, numadultos, numninnos, idfactura, idusuario, idestado, idhotel)
	values( fechaini, fechafin, numa, numn, idfac, idu, ide, idh );
$$;


ALTER FUNCTION public.makereserve(fechaini date, fechafin date, numa integer, numn integer, idfac integer, idu integer, ide integer, idh integer) OWNER TO fernando;

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
    nombreentrada character varying(30) NOT NULL,
    tipoentrada character varying(50) NOT NULL,
    precioentrada double precision NOT NULL,
    idestado integer NOT NULL,
    imgentrada character varying(50)
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
    idhotel integer NOT NULL,
    imghabitacion character varying(50),
    dethabitacioneng character varying(250)
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
    descrhoteleng character varying(300) DEFAULT 'n/a'::character varying NOT NULL,
    direchotel character varying(100) DEFAULT 'n/a'::character varying NOT NULL,
    idestado integer NOT NULL,
    imghotel character varying(50)
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
    idhotel integer NOT NULL,
    imgplan character varying(50)
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
    idhotel integer NOT NULL,
    imgtransporte character varying(50)
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

COPY entrada (codentrada, nombreentrada, tipoentrada, precioentrada, idestado, imgentrada) FROM stdin;
\.


--
-- Name: entrada_codentrada_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('entrada_codentrada_seq', 1, false);


--
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY estado (idestado, nomestado) FROM stdin;
1	California
2	Florida
3	Hawai
4	Illinois
5	Nevada
6	New York
7	Washington
\.


--
-- Name: estado_idestado_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('estado_idestado_seq', 7, true);


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

COPY habitacion (codhabitacion, maxperson, prchabitacion, dethabitacion, estadoreserva, idhotel, imghabitacion, dethabitacioneng) FROM stdin;
42	2	42.3500000000000014	no hay descripcion	0	17	\N	theres none
46	1	39.4500000000000028	no hay descripcion	0	17	\N	theres none
39	3	49.4099999999999966	no hay descripcion	0	20	\N	theres none
7	1	33.8500000000000014	no hay descripcion	0	13	\N	theres none
1	2	72.3299999999999983	no hay descripcion	0	14	\N	theres none
3	1	19.5500000000000007	no hay descripcion	0	14	\N	theres none
5	2	10.4800000000000004	no hay descripcion	0	24	\N	theres none
6	3	20.879999999999999	no hay descripcion	0	24	\N	theres none
26	1	75.7000000000000028	no hay descripcion	0	13	\N	theres none
32	3	50.7100000000000009	no hay descripcion	0	14	\N	theres none
33	2	50.8699999999999974	no hay descripcion	0	23	\N	theres none
34	3	44.2800000000000011	no hay descripcion	0	24	\N	theres none
35	1	23.9600000000000009	no hay descripcion	0	13	\N	theres none
36	3	90.3299999999999983	no hay descripcion	0	21	\N	theres none
38	1	20.6700000000000017	no hay descripcion	0	15	\N	theres none
40	1	87.1200000000000045	no hay descripcion	0	22	\N	theres none
41	3	90.4000000000000057	no hay descripcion	0	18	\N	theres none
43	2	40.8999999999999986	no hay descripcion	0	22	\N	theres none
44	1	99.3400000000000034	no hay descripcion	0	19	\N	theres none
45	1	38.8699999999999974	no hay descripcion	0	21	\N	theres none
47	3	95.4599999999999937	no hay descripcion	0	24	\N	theres none
48	1	90.0699999999999932	no hay descripcion	0	19	\N	theres none
49	3	13.1600000000000001	no hay descripcion	0	15	\N	theres none
50	1	34.2000000000000028	no hay descripcion	0	20	\N	theres none
37	2	27.3000000000000007	no hay descripcion	0	17	\N	theres none
4	1	80.3599999999999994	no hay descripcion	1	17	\N	theres none
2	1	18.9400000000000013	no hay descripcion	1	18	\N	theres none
18	3	63.7700000000000031	no hay descripcion	0	19	\N	theres none
31	2	61.6599999999999966	no hay descripcion	0	20	\N	theres none
8	2	22.3999999999999986	no hay descripcion	0	21	\N	theres none
9	3	12.6699999999999999	no hay descripcion	0	18	\N	theres none
10	2	96.7399999999999949	no hay descripcion	0	24	\N	theres none
11	3	29.4600000000000009	no hay descripcion	0	24	\N	theres none
12	2	91.269999999999996	no hay descripcion	0	23	\N	theres none
13	3	90.25	no hay descripcion	0	13	\N	theres none
14	1	77.4300000000000068	no hay descripcion	0	17	\N	theres none
15	2	79.2000000000000028	no hay descripcion	0	18	\N	theres none
16	3	89.5499999999999972	no hay descripcion	0	20	\N	theres none
17	1	18.0100000000000016	no hay descripcion	0	18	\N	theres none
19	3	87.7000000000000028	no hay descripcion	0	20	\N	theres none
20	2	81.75	no hay descripcion	0	21	\N	theres none
21	2	49.2899999999999991	no hay descripcion	0	17	\N	theres none
22	3	23.2600000000000016	no hay descripcion	0	23	\N	theres none
23	2	15.2799999999999994	no hay descripcion	0	19	\N	theres none
24	3	23.5700000000000003	no hay descripcion	0	23	\N	theres none
25	2	57.25	no hay descripcion	0	22	\N	theres none
27	1	69.7999999999999972	no hay descripcion	0	18	\N	theres none
28	2	29.9100000000000001	no hay descripcion	0	22	\N	theres none
29	3	77.2199999999999989	no hay descripcion	0	24	\N	theres none
30	1	67.2199999999999989	no hay descripcion	0	24	\N	theres none
\.


--
-- Name: habitacion_codhabitacion_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('habitacion_codhabitacion_seq', 50, true);


--
-- Data for Name: hotel; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY hotel (idhotel, nomhotel, lvlhotel, descrhotel, descrhoteleng, direchotel, idestado, imghotel) FROM stdin;
13	Cornell Hotel	2	'no hay'	'none'	40363 Aberg Court	1	\N
14	Drisco Hotel	5	'no hay'	'none'	7562 Maywood Drive	1	\N
15	Fairmont Grand del Mar hotel	5	'no hay'	'none'	3850 Huxley Lane	1	\N
16	Pearl hotel	1	'no hay'	'none'	0 Hanson Place	1	\N
17	Four seasons Resort	1	'no hay'	'none'	27 Ridgeway Lane	2	\N
18	Hampton hotel	0	'no hay'	'none'	27 Hooker Center	2	\N
19	Holilday inn	0	'no hay'	'none'	539 Doe Crossing Parkway	2	\N
20	Oceanside	3	'no hay'	'none'	0847 Debra Drive	2	\N
21	Embassy suites by hilton waikiki	1	'no hay'	'none'	45 Bonner Point	3	\N
22	Halekulani	0	'no hay'	'none'	55315 Holy Cross Place	3	\N
23	The Modern Honolulu	1	'no hay'	'none'	966 Luster Lane	3	\N
24	TI Hotel Waikiki	3	'no hay'	'none'	45546 Chinook Drive	3	\N
\.


--
-- Name: hotel_idhotel_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('hotel_idhotel_seq', 24, true);


--
-- Data for Name: plancomida; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY plancomida (codplan, nomplan, descrplan, idhotel, imgplan) FROM stdin;
\.


--
-- Name: plancomida_codplan_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('plancomida_codplan_seq', 1, false);


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reserva (codreserva, fechainicior, fechafinr, numadultos, numninnos, idfactura, idusuario, idestado, codtransporte, idhotel) FROM stdin;
70	2016-07-01	2016-07-08	1	0	20	2	2	\N	17
71	2016-07-01	2016-07-08	1	0	21	1	2	\N	18
\.


--
-- Name: reserva_codreserva_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('reserva_codreserva_seq', 71, true);


--
-- Data for Name: reservaxentrada; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxentrada (codreserva, codentrada, cantidad, fecha) FROM stdin;
\.


--
-- Data for Name: reservaxhabitacion; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxhabitacion (codreserva, codhabitacion, canttipo) FROM stdin;
70	4	1
71	2	1
\.


--
-- Data for Name: reservaxplan; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reservaxplan (codreserva, codplan, cantidad) FROM stdin;
\.


--
-- Data for Name: transporte; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY transporte (codtransporte, modelotransporte, numpasajeros, capalmacenaje, estadoreserva, idhotel, imgtransporte) FROM stdin;
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
-- Name: estado_nomestado_key; Type: CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_nomestado_key UNIQUE (nomestado);


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
-- Name: entrada_idestado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY entrada
    ADD CONSTRAINT entrada_idestado_fkey FOREIGN KEY (idestado) REFERENCES estado(idestado) ON DELETE CASCADE;


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

