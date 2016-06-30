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
    tipoentradaeng character varying(50) NOT NULL,
    precioentrada double precision NOT NULL,
    idestado integer NOT NULL
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
    dethabitacioneng character varying(250) DEFAULT 'n/a'::character varying NOT NULL,
    estadoreserva integer DEFAULT 0 NOT NULL,
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
    descrhoteleng character varying(300) DEFAULT 'n/a'::character varying NOT NULL,
    direchotel character varying(100) DEFAULT 'n/a'::character varying NOT NULL,
    idestado integer NOT NULL
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
    descrplan character varying(150) DEFAULT 'no hay'::character varying NOT NULL,
    descrplaneng character varying(150) DEFAULT 'there is none'::character varying NOT NULL,
    precioplan double precision DEFAULT 0 NOT NULL,
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
    idusuario integer NOT NULL,
    idestado integer NOT NULL,
    codtransporte integer,
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
    codtransporte integer NOT NULL,
    modelotransporte character varying(10) NOT NULL,
    numpasajeros integer DEFAULT 1 NOT NULL,
    prectransporte double precision DEFAULT 0.00 NOT NULL,
    estadoreserva integer NOT NULL,
    idhotel integer NOT NULL
);


ALTER TABLE transporte OWNER TO fernando;

--
-- Name: transporte_codtransporte_seq; Type: SEQUENCE; Schema: public; Owner: fernando
--

CREATE SEQUENCE transporte_codtransporte_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE transporte_codtransporte_seq OWNER TO fernando;

--
-- Name: transporte_codtransporte_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: fernando
--

ALTER SEQUENCE transporte_codtransporte_seq OWNED BY transporte.codtransporte;


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
-- Name: codtransporte; Type: DEFAULT; Schema: public; Owner: fernando
--

ALTER TABLE ONLY transporte ALTER COLUMN codtransporte SET DEFAULT nextval('transporte_codtransporte_seq'::regclass);


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

COPY entrada (codentrada, nombreentrada, tipoentrada, tipoentradaeng, precioentrada, idestado) FROM stdin;
55	Collection Frick	Un lugar muy acojedor	A very warming place	55.7100000000000009	15
56	High Line Broadway	Un lugar muy acojedor	A very warming place	83.6299999999999955	15
57	Empire State Building	Un lugar muy acojedor	A very warming place	67.7999999999999972	15
58	Hoover Dam Bypass	Un lugar muy acojedor	A very warming place	79.5900000000000034	14
59	The mob Museum	Un lugar muy acojedor	A very warming place	16.1999999999999993	14
60	Wynn las Vegas casino	Un lugar muy acojedor	A very warming place	58.5499999999999972	14
1	Golden Gate	no hay	there's none	72	1
2	Hollywood	no hay	there's none	47	1
61	Walt Disney World	Un lugar muy acojedor	A very warming place	19.620000000000001	7
62	Magic Kingdom Park	Un lugar muy acojedor	A very warming place	45.1700000000000017	7
63	Miami Beach 	Un lugar muy acojedor	A very warming place	50.8599999999999994	7
64	the wizarding	Un lugar muy acojedor	A very warming place	83.019999999999996	7
65	Millennium Park	Un lugar muy acojedor	A very warming place	14.5899999999999999	10
66	Pearl Harbor	Un lugar muy acojedor	A very warming place	24.9400000000000013	9
67	Diamond Head	Un lugar muy acojedor	A very warming place	48.7800000000000011	9
68	Makapuu lighthouse trail	Un lugar muy acojedor	A very warming place	16.2800000000000011	9
69	Isabella Museum	Un lugar muy acojedor	A very warming place	81.480000000000004	10
70	Arnold Arboretum	Un lugar muy acojedor	A very warming place	84.5300000000000011	10
71	USS museum	Un lugar muy acojedor	A very warming place	75.6800000000000068	10
72	Pink Jeep tours	Un lugar muy acojedor	A very warming place	21.8399999999999999	2
73	Sedona Chapel	Un lugar muy acojedor	A very warming place	27.8999999999999986	2
74	Oak creek Canyon	Un lugar muy acojedor	A very warming place	27.1600000000000001	2
75	Sabanna Historic District	Un lugar muy acojedor	A very warming place	56.8500000000000014	8
76	Bounaventure Cemetery	Un lugar muy acojedor	A very warming place	79.1800000000000068	8
77	Catedral of San Juan Bautista	Un lugar muy acojedor	A very warming place	73.7099999999999937	8
78	Titanic Museum	Un lugar muy acojedor	A very warming place	21.2899999999999991	13
79	Table rock Lake	Un lugar muy acojedor	A very warming place	63.1199999999999974	13
80	Showboat Branson Sell	Un lugar muy acojedor	A very warming place	36.4099999999999966	13
81	The Grand Ole	Un lugar muy acojedor	A very warming place	69.1899999999999977	17
82	Opry  RCA Studio B Lane	Un lugar muy acojedor	A very warming place	36.8800000000000026	17
83	Motor Museum	Un lugar muy acojedor	A very warming place	34.6000000000000014	17
84	Charleston Waterfront	Un lugar muy acojedor	A very warming place	82.7900000000000063	4
85	park Broadway at the beach	Un lugar muy acojedor	A very warming place	31.0599999999999987	4
86	Ripley’s Acuarium	Un lugar muy acojedor	A very warming place	15.2799999999999994	4
87	Presidential Library State	Un lugar muy acojedor	A very warming place	69.5900000000000034	18
88	capitol Zilker	Un lugar muy acojedor	A very warming place	72.230000000000004	18
89	Metropolitan Park	Un lugar muy acojedor	A very warming place	15.0800000000000001	18
90	Husky Homestead	Un lugar muy acojedor	A very warming place	36.7100000000000009	1
91	Park Road	Un lugar muy acojedor	A very warming place	25.129999999999999	1
92	Alaska Railroad Depot	Un lugar muy acojedor	A very warming place	15.0800000000000001	1
93	Custer State park	Un lugar muy acojedor	A very warming place	78.3900000000000006	6
94	Mount Rushmore	Un lugar muy acojedor	A very warming place	24.7300000000000004	6
95	national memorial	Un lugar muy acojedor	A very warming place	11.4800000000000004	6
96	Pikes peak	Un lugar muy acojedor	A very warming place	33.6199999999999974	5
97	Maroon Bells	Un lugar muy acojedor	A very warming place	54.7800000000000011	5
98	Colorado national monument	Un lugar muy acojedor	A very warming place	81.9000000000000057	5
99	Bryce Canyon	Un lugar muy acojedor	A very warming place	64.730000000000004	19
100	Best Friends Animal Sanctuary	Un lugar muy acojedor	A very warming place	66.4000000000000057	19
101	Sandia Peak Tramway	Un lugar muy acojedor	A very warming place	73.0100000000000051	16
102	Canyon Road	Un lugar muy acojedor	A very warming place	26.1600000000000001	16
\.


--
-- Name: entrada_codentrada_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('entrada_codentrada_seq', 102, true);


--
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY estado (idestado, nomestado) FROM stdin;
1	Alaska
2	Arizona
3	California
4	South Carolina
5	Colorado
6	North Dakota
7	Florida
8	Georgia
9	Hawaii
10	Illinois
11	Lousiana
12	Massachusetts
13	Missouri
14	Nevada
15	New York
16	New Mexico
17	Tennessee
18	Texas
19	Utah
\.


--
-- Name: estado_idestado_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('estado_idestado_seq', 19, true);


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

COPY habitacion (codhabitacion, maxperson, prchabitacion, dethabitacion, dethabitacioneng, estadoreserva, idhotel) FROM stdin;
1	1	59.25	increible suite para 3 o mas personas!	beautiful room for one person	0	52
2	2	144.129999999999995	bella habitacion para 2 personas	great suite for a big meeting with many people	0	31
401	1	59.25	increible suite para 3 o mas personas!	beautiful room for one person	0	52
4	1	108.310000000000002	increible suite para 3 o mas personas!	romantic room for 2 people	0	56
402	2	144.129999999999995	bella habitacion para 2 personas	great suite for a big meeting with many people	0	31
404	1	108.310000000000002	increible suite para 3 o mas personas!	romantic room for 2 people	0	56
9	3	137.219999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	52
10	1	43.5499999999999972	hermosa habitacion para 1 persona	romantic room for 2 people	0	44
11	2	157.780000000000001	bella habitacion para 2 personas	great suite for a big meeting with many people	0	24
12	3	51.4200000000000017	increible suite para 3 o mas personas!	beautiful room for one person	0	8
16	1	64.7399999999999949	increible suite para 3 o mas personas!	beautiful room for one person	0	39
17	2	147.740000000000009	increible suite para 3 o mas personas!	romantic room for 2 people	0	10
409	3	137.219999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	52
19	1	176.150000000000006	hermosa habitacion para 1 persona	romantic room for 2 people	0	1
20	2	141.419999999999987	increible suite para 3 o mas personas!	beautiful room for one person	0	30
410	1	43.5499999999999972	hermosa habitacion para 1 persona	romantic room for 2 people	0	44
22	1	196.620000000000005	bella habitacion para 2 personas	romantic room for 2 people	0	27
23	2	43.8200000000000003	hermosa habitacion para 1 persona	beautiful room for one person	0	40
411	2	157.780000000000001	bella habitacion para 2 personas	great suite for a big meeting with many people	0	24
412	3	51.4200000000000017	increible suite para 3 o mas personas!	beautiful room for one person	0	8
26	2	33.9600000000000009	bella habitacion para 2 personas	beautiful room for one person	0	52
27	3	22.3099999999999987	hermosa habitacion para 1 persona	beautiful room for one person	0	36
30	3	64.1700000000000017	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	27
416	1	64.7399999999999949	increible suite para 3 o mas personas!	beautiful room for one person	0	39
33	3	118.969999999999999	bella habitacion para 2 personas	beautiful room for one person	0	13
417	2	147.740000000000009	increible suite para 3 o mas personas!	romantic room for 2 people	0	10
419	1	176.150000000000006	hermosa habitacion para 1 persona	romantic room for 2 people	0	1
37	1	125.359999999999999	increible suite para 3 o mas personas!	romantic room for 2 people	0	50
38	2	197.360000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	52
420	2	141.419999999999987	increible suite para 3 o mas personas!	beautiful room for one person	0	30
40	1	163.52000000000001	bella habitacion para 2 personas	romantic room for 2 people	0	58
41	2	198.689999999999998	bella habitacion para 2 personas	beautiful room for one person	0	34
43	1	188.240000000000009	bella habitacion para 2 personas	romantic room for 2 people	0	34
44	2	174.449999999999989	hermosa habitacion para 1 persona	beautiful room for one person	0	56
422	1	196.620000000000005	bella habitacion para 2 personas	romantic room for 2 people	0	27
46	1	139.550000000000011	hermosa habitacion para 1 persona	romantic room for 2 people	0	56
423	2	43.8200000000000003	hermosa habitacion para 1 persona	beautiful room for one person	0	40
48	3	58.9699999999999989	hermosa habitacion para 1 persona	romantic room for 2 people	0	42
49	1	134.050000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	31
52	1	51.1700000000000017	increible suite para 3 o mas personas!	beautiful room for one person	0	8
426	2	33.9600000000000009	bella habitacion para 2 personas	beautiful room for one person	0	52
54	3	126.719999999999999	increible suite para 3 o mas personas!	romantic room for 2 people	0	44
427	3	22.3099999999999987	hermosa habitacion para 1 persona	beautiful room for one person	0	36
430	3	64.1700000000000017	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	27
60	3	41.5200000000000031	bella habitacion para 2 personas	great suite for a big meeting with many people	0	48
62	2	62.9299999999999997	hermosa habitacion para 1 persona	beautiful room for one person	0	13
433	3	118.969999999999999	bella habitacion para 2 personas	beautiful room for one person	0	13
65	2	193.72999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	18
66	3	185.580000000000013	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	22
437	1	125.359999999999999	increible suite para 3 o mas personas!	romantic room for 2 people	0	50
69	3	24.4699999999999989	hermosa habitacion para 1 persona	beautiful room for one person	0	14
70	1	23.9899999999999984	hermosa habitacion para 1 persona	beautiful room for one person	0	44
71	2	154.719999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	43
72	3	167.680000000000007	bella habitacion para 2 personas	romantic room for 2 people	0	14
438	2	197.360000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	52
74	2	22.1400000000000006	increible suite para 3 o mas personas!	beautiful room for one person	0	45
75	3	97.3199999999999932	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	26
76	1	101.480000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	1
77	2	118.950000000000003	increible suite para 3 o mas personas!	beautiful room for one person	0	32
440	1	163.52000000000001	bella habitacion para 2 personas	romantic room for 2 people	0	58
441	2	198.689999999999998	bella habitacion para 2 personas	beautiful room for one person	0	34
82	1	20.7399999999999984	bella habitacion para 2 personas	beautiful room for one person	0	12
83	2	136.039999999999992	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	13
443	1	188.240000000000009	bella habitacion para 2 personas	romantic room for 2 people	0	34
85	1	187.840000000000003	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	40
86	2	116.519999999999996	hermosa habitacion para 1 persona	romantic room for 2 people	0	1
444	2	174.449999999999989	hermosa habitacion para 1 persona	beautiful room for one person	0	56
89	2	42	hermosa habitacion para 1 persona	romantic room for 2 people	0	31
446	1	139.550000000000011	hermosa habitacion para 1 persona	romantic room for 2 people	0	56
91	1	125.159999999999997	bella habitacion para 2 personas	romantic room for 2 people	0	53
92	2	195.919999999999987	increible suite para 3 o mas personas!	romantic room for 2 people	0	6
93	3	63.4799999999999969	bella habitacion para 2 personas	great suite for a big meeting with many people	0	22
95	2	115.909999999999997	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	54
448	3	58.9699999999999989	hermosa habitacion para 1 persona	romantic room for 2 people	0	42
97	1	189.379999999999995	increible suite para 3 o mas personas!	romantic room for 2 people	0	2
449	1	134.050000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	31
100	1	95.9399999999999977	bella habitacion para 2 personas	romantic room for 2 people	0	42
101	2	93.980000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	38
102	3	47.0900000000000034	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	26
452	1	51.1700000000000017	increible suite para 3 o mas personas!	beautiful room for one person	0	8
454	3	126.719999999999999	increible suite para 3 o mas personas!	romantic room for 2 people	0	44
107	2	107.640000000000001	hermosa habitacion para 1 persona	beautiful room for one person	0	15
108	3	48.3699999999999974	increible suite para 3 o mas personas!	beautiful room for one person	0	17
111	3	180.800000000000011	increible suite para 3 o mas personas!	beautiful room for one person	0	4
112	1	118.010000000000005	bella habitacion para 2 personas	great suite for a big meeting with many people	0	17
115	1	21.3599999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	58
116	2	186.419999999999987	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	37
118	1	193.97999999999999	hermosa habitacion para 1 persona	romantic room for 2 people	0	6
119	2	57.8599999999999994	bella habitacion para 2 personas	romantic room for 2 people	0	25
460	3	41.5200000000000031	bella habitacion para 2 personas	great suite for a big meeting with many people	0	48
121	1	189.180000000000007	increible suite para 3 o mas personas!	beautiful room for one person	0	32
462	2	62.9299999999999997	hermosa habitacion para 1 persona	beautiful room for one person	0	13
465	2	193.72999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	18
128	2	135.969999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	35
129	3	166.719999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	22
130	1	135.719999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	33
131	2	181.379999999999995	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	12
132	3	193.060000000000002	bella habitacion para 2 personas	beautiful room for one person	0	55
133	1	122.379999999999995	bella habitacion para 2 personas	romantic room for 2 people	0	6
466	3	185.580000000000013	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	22
135	3	133.77000000000001	bella habitacion para 2 personas	beautiful room for one person	0	37
136	1	176.240000000000009	bella habitacion para 2 personas	great suite for a big meeting with many people	0	56
137	2	91.1400000000000006	bella habitacion para 2 personas	romantic room for 2 people	0	46
138	3	154.5	hermosa habitacion para 1 persona	romantic room for 2 people	0	29
140	2	96.5100000000000051	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
142	1	125.859999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	18
469	3	24.4699999999999989	hermosa habitacion para 1 persona	beautiful room for one person	0	14
470	1	23.9899999999999984	hermosa habitacion para 1 persona	beautiful room for one person	0	44
471	2	154.719999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	43
472	3	167.680000000000007	bella habitacion para 2 personas	romantic room for 2 people	0	14
474	2	22.1400000000000006	increible suite para 3 o mas personas!	beautiful room for one person	0	45
475	3	97.3199999999999932	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	26
150	3	99.5900000000000034	hermosa habitacion para 1 persona	beautiful room for one person	0	37
151	1	42.3699999999999974	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	45
476	1	101.480000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	1
153	3	165.52000000000001	bella habitacion para 2 personas	beautiful room for one person	0	6
477	2	118.950000000000003	increible suite para 3 o mas personas!	beautiful room for one person	0	32
159	3	74.9300000000000068	hermosa habitacion para 1 persona	beautiful room for one person	0	22
160	1	65.2099999999999937	bella habitacion para 2 personas	great suite for a big meeting with many people	0	21
482	1	20.7399999999999984	bella habitacion para 2 personas	beautiful room for one person	0	12
483	2	136.039999999999992	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	13
163	1	21.0100000000000016	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
165	3	43.7100000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	36
166	1	150.27000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	49
167	2	39.7000000000000028	bella habitacion para 2 personas	great suite for a big meeting with many people	0	49
485	1	187.840000000000003	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	40
169	1	56.2299999999999969	increible suite para 3 o mas personas!	romantic room for 2 people	0	11
170	2	168.639999999999986	hermosa habitacion para 1 persona	beautiful room for one person	0	56
171	3	93.3100000000000023	increible suite para 3 o mas personas!	romantic room for 2 people	0	55
486	2	116.519999999999996	hermosa habitacion para 1 persona	romantic room for 2 people	0	1
174	3	152.719999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	18
175	1	66.6800000000000068	bella habitacion para 2 personas	romantic room for 2 people	0	55
176	2	98.1299999999999955	increible suite para 3 o mas personas!	beautiful room for one person	0	6
489	2	42	hermosa habitacion para 1 persona	romantic room for 2 people	0	31
179	2	110.680000000000007	increible suite para 3 o mas personas!	romantic room for 2 people	0	53
491	1	125.159999999999997	bella habitacion para 2 personas	romantic room for 2 people	0	53
492	2	195.919999999999987	increible suite para 3 o mas personas!	romantic room for 2 people	0	6
493	3	63.4799999999999969	bella habitacion para 2 personas	great suite for a big meeting with many people	0	22
184	1	153.469999999999999	bella habitacion para 2 personas	romantic room for 2 people	0	6
495	2	115.909999999999997	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	54
497	1	189.379999999999995	increible suite para 3 o mas personas!	romantic room for 2 people	0	2
190	1	114.269999999999996	increible suite para 3 o mas personas!	romantic room for 2 people	0	22
194	2	176.780000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	31
195	3	39.3100000000000023	hermosa habitacion para 1 persona	beautiful room for one person	0	22
500	1	95.9399999999999977	bella habitacion para 2 personas	romantic room for 2 people	0	42
197	2	110.609999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	37
501	2	93.980000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	38
502	3	47.0900000000000034	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	26
201	3	159.139999999999986	hermosa habitacion para 1 persona	beautiful room for one person	0	57
204	3	56.3500000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	8
205	1	179.719999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	52
206	2	143.819999999999993	increible suite para 3 o mas personas!	romantic room for 2 people	0	1
207	3	179.710000000000008	increible suite para 3 o mas personas!	romantic room for 2 people	0	21
208	1	91.1200000000000045	bella habitacion para 2 personas	romantic room for 2 people	0	19
209	2	37.7100000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
507	2	107.640000000000001	hermosa habitacion para 1 persona	beautiful room for one person	0	15
212	2	103	hermosa habitacion para 1 persona	romantic room for 2 people	0	12
508	3	48.3699999999999974	increible suite para 3 o mas personas!	beautiful room for one person	0	17
214	1	139.819999999999993	bella habitacion para 2 personas	romantic room for 2 people	0	19
215	2	89.4399999999999977	bella habitacion para 2 personas	beautiful room for one person	0	38
217	1	89.019999999999996	hermosa habitacion para 1 persona	beautiful room for one person	0	20
218	2	38.7100000000000009	hermosa habitacion para 1 persona	beautiful room for one person	0	14
219	3	94.6099999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	3
220	1	92.0699999999999932	bella habitacion para 2 personas	great suite for a big meeting with many people	0	28
221	2	164.870000000000005	bella habitacion para 2 personas	romantic room for 2 people	0	23
222	3	162.419999999999987	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	22
223	1	159.189999999999998	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	48
224	2	99.519999999999996	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	28
226	1	156.180000000000007	bella habitacion para 2 personas	beautiful room for one person	0	55
227	2	191.009999999999991	bella habitacion para 2 personas	beautiful room for one person	0	44
228	3	147.389999999999986	hermosa habitacion para 1 persona	romantic room for 2 people	0	36
511	3	180.800000000000011	increible suite para 3 o mas personas!	beautiful room for one person	0	4
230	2	27.0799999999999983	bella habitacion para 2 personas	beautiful room for one person	0	28
512	1	118.010000000000005	bella habitacion para 2 personas	great suite for a big meeting with many people	0	17
233	2	176.740000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	5
515	1	21.3599999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	58
236	2	147.530000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	23
237	3	67.8400000000000034	bella habitacion para 2 personas	beautiful room for one person	0	41
516	2	186.419999999999987	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	37
518	1	193.97999999999999	hermosa habitacion para 1 persona	romantic room for 2 people	0	6
519	2	57.8599999999999994	bella habitacion para 2 personas	romantic room for 2 people	0	25
521	1	189.180000000000007	increible suite para 3 o mas personas!	beautiful room for one person	0	32
245	2	89.5900000000000034	hermosa habitacion para 1 persona	beautiful room for one person	0	21
249	3	88.1599999999999966	hermosa habitacion para 1 persona	romantic room for 2 people	0	46
252	3	181.27000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	50
253	1	112.700000000000003	bella habitacion para 2 personas	beautiful room for one person	0	15
528	2	135.969999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	35
256	1	24.9200000000000017	bella habitacion para 2 personas	great suite for a big meeting with many people	0	54
257	2	32.759999999999998	bella habitacion para 2 personas	beautiful room for one person	0	30
529	3	166.719999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	22
530	1	135.719999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	33
260	2	166.050000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
531	2	181.379999999999995	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	12
532	3	193.060000000000002	bella habitacion para 2 personas	beautiful room for one person	0	55
263	2	129.129999999999995	hermosa habitacion para 1 persona	beautiful room for one person	0	49
264	3	129.590000000000003	hermosa habitacion para 1 persona	romantic room for 2 people	0	21
533	1	122.379999999999995	bella habitacion para 2 personas	romantic room for 2 people	0	6
535	3	133.77000000000001	bella habitacion para 2 personas	beautiful room for one person	0	37
536	1	176.240000000000009	bella habitacion para 2 personas	great suite for a big meeting with many people	0	56
537	2	91.1400000000000006	bella habitacion para 2 personas	romantic room for 2 people	0	46
270	3	188.159999999999997	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	57
538	3	154.5	hermosa habitacion para 1 persona	romantic room for 2 people	0	29
272	2	167.72999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	22
273	3	104.799999999999997	hermosa habitacion para 1 persona	romantic room for 2 people	0	45
540	2	96.5100000000000051	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
276	3	170.800000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	29
277	1	71.9599999999999937	increible suite para 3 o mas personas!	romantic room for 2 people	0	37
278	2	119.609999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	2
542	1	125.859999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	18
281	2	91.5400000000000063	bella habitacion para 2 personas	great suite for a big meeting with many people	0	51
282	3	115.079999999999998	bella habitacion para 2 personas	romantic room for 2 people	0	24
283	1	51.7800000000000011	bella habitacion para 2 personas	romantic room for 2 people	0	50
288	3	120.870000000000005	hermosa habitacion para 1 persona	beautiful room for one person	0	30
292	1	116.049999999999997	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	35
550	3	99.5900000000000034	hermosa habitacion para 1 persona	beautiful room for one person	0	37
551	1	42.3699999999999974	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	45
295	1	21.8299999999999983	bella habitacion para 2 personas	romantic room for 2 people	0	21
297	3	31.1900000000000013	bella habitacion para 2 personas	romantic room for 2 people	0	52
553	3	165.52000000000001	bella habitacion para 2 personas	beautiful room for one person	0	6
299	2	158.689999999999998	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
301	1	112.230000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	37
302	2	145.469999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	30
304	1	183.75	increible suite para 3 o mas personas!	romantic room for 2 people	0	4
305	2	163.240000000000009	bella habitacion para 2 personas	romantic room for 2 people	0	6
306	3	180.080000000000013	hermosa habitacion para 1 persona	romantic room for 2 people	0	52
307	1	98.9399999999999977	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	11
308	2	85.3700000000000045	hermosa habitacion para 1 persona	beautiful room for one person	0	55
311	2	131.610000000000014	increible suite para 3 o mas personas!	romantic room for 2 people	0	57
559	3	74.9300000000000068	hermosa habitacion para 1 persona	beautiful room for one person	0	22
314	2	44.0300000000000011	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	9
560	1	65.2099999999999937	bella habitacion para 2 personas	great suite for a big meeting with many people	0	21
316	1	195.490000000000009	bella habitacion para 2 personas	beautiful room for one person	0	1
317	2	188.870000000000005	increible suite para 3 o mas personas!	beautiful room for one person	0	8
318	3	168.530000000000001	increible suite para 3 o mas personas!	romantic room for 2 people	0	21
319	1	165.629999999999995	increible suite para 3 o mas personas!	beautiful room for one person	0	35
321	3	27.0100000000000016	hermosa habitacion para 1 persona	beautiful room for one person	0	24
322	1	23.6900000000000013	hermosa habitacion para 1 persona	beautiful room for one person	0	14
563	1	21.0100000000000016	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
325	1	147.439999999999998	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	32
565	3	43.7100000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	36
327	3	194.340000000000003	increible suite para 3 o mas personas!	romantic room for 2 people	0	20
566	1	150.27000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	49
329	2	135.289999999999992	increible suite para 3 o mas personas!	beautiful room for one person	0	10
330	3	71.7099999999999937	increible suite para 3 o mas personas!	beautiful room for one person	0	50
567	2	39.7000000000000028	bella habitacion para 2 personas	great suite for a big meeting with many people	0	49
569	1	56.2299999999999969	increible suite para 3 o mas personas!	romantic room for 2 people	0	11
570	2	168.639999999999986	hermosa habitacion para 1 persona	beautiful room for one person	0	56
335	2	84.2199999999999989	increible suite para 3 o mas personas!	romantic room for 2 people	0	49
336	3	142.349999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	56
571	3	93.3100000000000023	increible suite para 3 o mas personas!	romantic room for 2 people	0	55
339	3	136.27000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	8
340	1	72.0400000000000063	increible suite para 3 o mas personas!	beautiful room for one person	0	4
341	2	84.7099999999999937	increible suite para 3 o mas personas!	beautiful room for one person	0	11
574	3	152.719999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	18
344	2	85.9200000000000017	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	53
345	3	104.219999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	36
346	1	171.650000000000006	bella habitacion para 2 personas	great suite for a big meeting with many people	0	13
575	1	66.6800000000000068	bella habitacion para 2 personas	romantic room for 2 people	0	55
348	3	151.72999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	56
576	2	98.1299999999999955	increible suite para 3 o mas personas!	beautiful room for one person	0	6
350	2	37.6199999999999974	bella habitacion para 2 personas	great suite for a big meeting with many people	0	43
352	1	105.290000000000006	increible suite para 3 o mas personas!	romantic room for 2 people	0	23
353	2	90.5400000000000063	bella habitacion para 2 personas	beautiful room for one person	0	4
355	1	150.509999999999991	bella habitacion para 2 personas	great suite for a big meeting with many people	0	35
356	2	170.300000000000011	increible suite para 3 o mas personas!	beautiful room for one person	0	46
357	3	102.560000000000002	increible suite para 3 o mas personas!	romantic room for 2 people	0	46
579	2	110.680000000000007	increible suite para 3 o mas personas!	romantic room for 2 people	0	53
361	1	133.210000000000008	bella habitacion para 2 personas	beautiful room for one person	0	44
362	2	139.849999999999994	hermosa habitacion para 1 persona	romantic room for 2 people	0	32
363	3	33.7899999999999991	bella habitacion para 2 personas	great suite for a big meeting with many people	0	12
584	1	153.469999999999999	bella habitacion para 2 personas	romantic room for 2 people	0	6
368	2	108.060000000000002	bella habitacion para 2 personas	great suite for a big meeting with many people	0	29
371	2	166.909999999999997	increible suite para 3 o mas personas!	beautiful room for one person	0	22
373	1	59.7100000000000009	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
374	2	100.170000000000002	hermosa habitacion para 1 persona	beautiful room for one person	0	1
375	3	100.140000000000001	bella habitacion para 2 personas	beautiful room for one person	0	9
376	1	107.290000000000006	bella habitacion para 2 personas	beautiful room for one person	0	56
377	2	158.469999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	27
379	1	131.150000000000006	hermosa habitacion para 1 persona	beautiful room for one person	0	27
590	1	114.269999999999996	increible suite para 3 o mas personas!	romantic room for 2 people	0	22
381	3	89.3799999999999955	increible suite para 3 o mas personas!	beautiful room for one person	0	52
384	3	137.610000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	17
385	1	29.1600000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	11
386	2	122.340000000000003	hermosa habitacion para 1 persona	beautiful room for one person	0	18
388	1	116.189999999999998	increible suite para 3 o mas personas!	beautiful room for one person	0	4
389	2	31.6000000000000014	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	53
390	3	149.77000000000001	increible suite para 3 o mas personas!	beautiful room for one person	0	37
594	2	176.780000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	31
392	2	75.4300000000000068	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	10
393	3	85.4699999999999989	bella habitacion para 2 personas	romantic room for 2 people	0	50
595	3	39.3100000000000023	hermosa habitacion para 1 persona	beautiful room for one person	0	22
396	3	192.759999999999991	hermosa habitacion para 1 persona	romantic room for 2 people	0	53
597	2	110.609999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	37
399	3	178.969999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	23
601	3	159.139999999999986	hermosa habitacion para 1 persona	beautiful room for one person	0	57
604	3	56.3500000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	8
605	1	179.719999999999999	bella habitacion para 2 personas	great suite for a big meeting with many people	0	52
606	2	143.819999999999993	increible suite para 3 o mas personas!	romantic room for 2 people	0	1
607	3	179.710000000000008	increible suite para 3 o mas personas!	romantic room for 2 people	0	21
608	1	91.1200000000000045	bella habitacion para 2 personas	romantic room for 2 people	0	19
609	2	37.7100000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	16
612	2	103	hermosa habitacion para 1 persona	romantic room for 2 people	0	12
614	1	139.819999999999993	bella habitacion para 2 personas	romantic room for 2 people	0	19
615	2	89.4399999999999977	bella habitacion para 2 personas	beautiful room for one person	0	38
617	1	89.019999999999996	hermosa habitacion para 1 persona	beautiful room for one person	0	20
618	2	38.7100000000000009	hermosa habitacion para 1 persona	beautiful room for one person	0	14
619	3	94.6099999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	3
620	1	92.0699999999999932	bella habitacion para 2 personas	great suite for a big meeting with many people	0	28
621	2	164.870000000000005	bella habitacion para 2 personas	romantic room for 2 people	0	23
622	3	162.419999999999987	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	22
623	1	159.189999999999998	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	48
624	2	99.519999999999996	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	28
626	1	156.180000000000007	bella habitacion para 2 personas	beautiful room for one person	0	55
627	2	191.009999999999991	bella habitacion para 2 personas	beautiful room for one person	0	44
628	3	147.389999999999986	hermosa habitacion para 1 persona	romantic room for 2 people	0	36
630	2	27.0799999999999983	bella habitacion para 2 personas	beautiful room for one person	0	28
633	2	176.740000000000009	hermosa habitacion para 1 persona	romantic room for 2 people	0	5
636	2	147.530000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	23
637	3	67.8400000000000034	bella habitacion para 2 personas	beautiful room for one person	0	41
645	2	89.5900000000000034	hermosa habitacion para 1 persona	beautiful room for one person	0	21
649	3	88.1599999999999966	hermosa habitacion para 1 persona	romantic room for 2 people	0	46
652	3	181.27000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	50
653	1	112.700000000000003	bella habitacion para 2 personas	beautiful room for one person	0	15
656	1	24.9200000000000017	bella habitacion para 2 personas	great suite for a big meeting with many people	0	54
657	2	32.759999999999998	bella habitacion para 2 personas	beautiful room for one person	0	30
660	2	166.050000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
663	2	129.129999999999995	hermosa habitacion para 1 persona	beautiful room for one person	0	49
664	3	129.590000000000003	hermosa habitacion para 1 persona	romantic room for 2 people	0	21
670	3	188.159999999999997	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	57
672	2	167.72999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	22
673	3	104.799999999999997	hermosa habitacion para 1 persona	romantic room for 2 people	0	45
676	3	170.800000000000011	increible suite para 3 o mas personas!	romantic room for 2 people	0	29
677	1	71.9599999999999937	increible suite para 3 o mas personas!	romantic room for 2 people	0	37
678	2	119.609999999999999	increible suite para 3 o mas personas!	beautiful room for one person	0	2
681	2	91.5400000000000063	bella habitacion para 2 personas	great suite for a big meeting with many people	0	51
682	3	115.079999999999998	bella habitacion para 2 personas	romantic room for 2 people	0	24
683	1	51.7800000000000011	bella habitacion para 2 personas	romantic room for 2 people	0	50
688	3	120.870000000000005	hermosa habitacion para 1 persona	beautiful room for one person	0	30
692	1	116.049999999999997	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	35
695	1	21.8299999999999983	bella habitacion para 2 personas	romantic room for 2 people	0	21
697	3	31.1900000000000013	bella habitacion para 2 personas	romantic room for 2 people	0	52
699	2	158.689999999999998	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
701	1	112.230000000000004	bella habitacion para 2 personas	great suite for a big meeting with many people	0	37
702	2	145.469999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	30
704	1	183.75	increible suite para 3 o mas personas!	romantic room for 2 people	0	4
705	2	163.240000000000009	bella habitacion para 2 personas	romantic room for 2 people	0	6
706	3	180.080000000000013	hermosa habitacion para 1 persona	romantic room for 2 people	0	52
707	1	98.9399999999999977	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	11
708	2	85.3700000000000045	hermosa habitacion para 1 persona	beautiful room for one person	0	55
711	2	131.610000000000014	increible suite para 3 o mas personas!	romantic room for 2 people	0	57
714	2	44.0300000000000011	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	9
716	1	195.490000000000009	bella habitacion para 2 personas	beautiful room for one person	0	1
717	2	188.870000000000005	increible suite para 3 o mas personas!	beautiful room for one person	0	8
718	3	168.530000000000001	increible suite para 3 o mas personas!	romantic room for 2 people	0	21
719	1	165.629999999999995	increible suite para 3 o mas personas!	beautiful room for one person	0	35
721	3	27.0100000000000016	hermosa habitacion para 1 persona	beautiful room for one person	0	24
722	1	23.6900000000000013	hermosa habitacion para 1 persona	beautiful room for one person	0	14
725	1	147.439999999999998	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	32
727	3	194.340000000000003	increible suite para 3 o mas personas!	romantic room for 2 people	0	20
729	2	135.289999999999992	increible suite para 3 o mas personas!	beautiful room for one person	0	10
730	3	71.7099999999999937	increible suite para 3 o mas personas!	beautiful room for one person	0	50
735	2	84.2199999999999989	increible suite para 3 o mas personas!	romantic room for 2 people	0	49
736	3	142.349999999999994	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	56
739	3	136.27000000000001	hermosa habitacion para 1 persona	romantic room for 2 people	0	8
740	1	72.0400000000000063	increible suite para 3 o mas personas!	beautiful room for one person	0	4
741	2	84.7099999999999937	increible suite para 3 o mas personas!	beautiful room for one person	0	11
744	2	85.9200000000000017	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	53
745	3	104.219999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	36
746	1	171.650000000000006	bella habitacion para 2 personas	great suite for a big meeting with many people	0	13
748	3	151.72999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	56
750	2	37.6199999999999974	bella habitacion para 2 personas	great suite for a big meeting with many people	0	43
752	1	105.290000000000006	increible suite para 3 o mas personas!	romantic room for 2 people	0	23
753	2	90.5400000000000063	bella habitacion para 2 personas	beautiful room for one person	0	4
755	1	150.509999999999991	bella habitacion para 2 personas	great suite for a big meeting with many people	0	35
756	2	170.300000000000011	increible suite para 3 o mas personas!	beautiful room for one person	0	46
757	3	102.560000000000002	increible suite para 3 o mas personas!	romantic room for 2 people	0	46
761	1	133.210000000000008	bella habitacion para 2 personas	beautiful room for one person	0	44
762	2	139.849999999999994	hermosa habitacion para 1 persona	romantic room for 2 people	0	32
763	3	33.7899999999999991	bella habitacion para 2 personas	great suite for a big meeting with many people	0	12
768	2	108.060000000000002	bella habitacion para 2 personas	great suite for a big meeting with many people	0	29
771	2	166.909999999999997	increible suite para 3 o mas personas!	beautiful room for one person	0	22
773	1	59.7100000000000009	increible suite para 3 o mas personas!	romantic room for 2 people	0	12
774	2	100.170000000000002	hermosa habitacion para 1 persona	beautiful room for one person	0	1
775	3	100.140000000000001	bella habitacion para 2 personas	beautiful room for one person	0	9
776	1	107.290000000000006	bella habitacion para 2 personas	beautiful room for one person	0	56
777	2	158.469999999999999	hermosa habitacion para 1 persona	beautiful room for one person	0	27
779	1	131.150000000000006	hermosa habitacion para 1 persona	beautiful room for one person	0	27
781	3	89.3799999999999955	increible suite para 3 o mas personas!	beautiful room for one person	0	52
784	3	137.610000000000014	increible suite para 3 o mas personas!	great suite for a big meeting with many people	0	17
785	1	29.1600000000000001	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	11
786	2	122.340000000000003	hermosa habitacion para 1 persona	beautiful room for one person	0	18
788	1	116.189999999999998	increible suite para 3 o mas personas!	beautiful room for one person	0	4
789	2	31.6000000000000014	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	53
790	3	149.77000000000001	increible suite para 3 o mas personas!	beautiful room for one person	0	37
792	2	75.4300000000000068	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	10
793	3	85.4699999999999989	bella habitacion para 2 personas	romantic room for 2 people	0	50
796	3	192.759999999999991	hermosa habitacion para 1 persona	romantic room for 2 people	0	53
799	3	178.969999999999999	hermosa habitacion para 1 persona	great suite for a big meeting with many people	0	23
\.


--
-- Name: habitacion_codhabitacion_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('habitacion_codhabitacion_seq', 800, true);


--
-- Data for Name: hotel; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY hotel (idhotel, nomhotel, lvlhotel, descrhotel, descrhoteleng, direchotel, idestado) FROM stdin;
1	Alyeska hotel	5	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	67058 Oriole Junction	1
2	Sheraton Anchorage	4	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	0 Morningstar Pass	1
3	Alyeska hotel	3	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	718 Welch Junction	1
4	Adobe grand villas	5	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	0 Farmco Terrace	2
5	Orchards in Sedona	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	5 Forest Run Court	2
6	Cornell Hotel	2	'no hay'	'none'	40363 Aberg Court	3
7	Drisco Hotel	5	'no hay'	'none'	7562 Maywood Drive	3
8	Fairmont Grand del Mar hotel	5	'no hay'	'none'	3850 Huxley Lane	3
9	Pearl hotel	3	'no hay'	'none'	0 Hanson Place	3
10	Dunes village resort	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	21 Waxwing Street	4
11	Holliday inn at the pavilion	4	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	72952 Dottie Road	4
12	Marina inn at grande Dunes	2	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	07 Vernon Pass	4
13	Dunes village resort	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	72913 David Way	4
14	Sonnenalp Hotel	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	3617 Artisan Plaza	5
15	The inn at lost Creek	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	077 Corry Junction	5
16	Tivoli Lodge hotel	1	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	2 Garrison Street	5
17	Cambria Hotel & suites rapid City	3	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	50876 Hovde Lane	6
18	hotel hampton inn & suites watertown	5	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	70147 Hazelcrest Place	6
19	Four seasons Resort	1	'no hay'	'none'	87 Ridgeway Lane	7
20	Hampton hotel	4	'no hay'	'none'	87 Hooker Center	7
21	Holilday inn	3	'no hay'	'none'	539 Doe Crossing Parkway	7
22	Oceanside	3	'no hay'	'none'	0847 Debra Drive	7
23	Andaz Savannah hotel	3	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	6717 Roth Crossing	8
24	Embassy Suite by hilton Savannah	3	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	56 Tomscot Trail	8
25	Embassy suites by hilton waikiki	3	'no hay'	'none'	45 Bonner Point	9
26	Halekulani	5	'no hay'	'none'	551015 Holy Cross Place	9
27	The Modern Honolulu	4	'no hay'	'none'	966 Luster Lane	9
28	TI Hotel Waikiki	4	'no hay'	'none'	45546 Chinook Drive	9
29	Four seasons Chicago	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	30853 Shelley Street	10
30	Park Hyatt	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	3 La Follette Lane	10
31	Talbott hotel	3	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	911 Homewood Street	10
32	Tompson Hotel	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	2152 Farmco Center	10
33	Bourbon Orleans Hotel	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	2819 Cherokee Street	11
34	Drury inn & suites	3	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	89773 Harbort Crossing	11
35	Grenoble house hote	3	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	01 Northfield Road	11
36	boston harbor hotel	2	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	5524 Dexter Alley	12
37	seaport boston hotel	3	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	297 Granby Terrace	12
38	The inn at longwood medical	1	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	5496 Brickson Park Crossing	12
39	Branso's best hotel	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	9225 Erie Center	13
40	Comfort inn & suites Branson	4	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	68 Kings Center	13
41	Meadows Hampton inn Branson	3	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	05384 Morningstar Park	13
42	Crosby Street hotel	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	22 Cambridge Junction	14
43	Dream Down town	4	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	657 Nobel Trail	14
44	Giraffe Hotel	1	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	139 Arapahoe Way	14
45	the bryant park hotel	5	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	190 Quincy Terrace	14
46	library hotel	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	73 Cardinal Crossing	15
48	the quin	1	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	96 Hazelcrest Road	15
49	the renwick hotel	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	5 Bonner Court	15
50	Inn of the five graces	4	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	2084 Nobel Circle	16
51	inn on the alameda	2	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	37 Burning Wood Junction	16
52	Hermitage Hotel	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	29503 Fairview Way	17
53	The capitol Hotel	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	8 Basil Center	17
54	Downtown Nashville	3	Lujoso hotel de lujo	Luxurios Luxury Hotel for your needs	606 Sunbrook Hill	17
55	Hyatt Regendy Austin	2	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	7 Onsgard Alley	18
56	Lone star court	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	0892 Coleman Road	18
57	Desert pearl inn	1	hermoso hotel de ultima tecnologia	beautiful hotel with the latest technology	31 Meadow Valley Street	19
58	The inn at Entrada	1	hotel para una estadia tranquila	an hotel wich birngs you closer to nature	98 Boyd Junction	19
\.


--
-- Name: hotel_idhotel_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('hotel_idhotel_seq', 58, true);


--
-- Data for Name: plancomida; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY plancomida (codplan, nomplan, descrplan, descrplaneng, precioplan, idhotel) FROM stdin;
1	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.2799999999999994	1
2	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.1199999999999992	2
3	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.41999999999999993	3
4	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.39000000000000057	4
5	Simple	Desayuno y cena incluidas	Breakfast and dinner included	5.24000000000000021	5
6	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.60999999999999943	6
7	Simple	Desayuno y cena incluidas	Breakfast and dinner included	3.68999999999999995	7
8	Simple	Desayuno y cena incluidas	Breakfast and dinner included	11.6099999999999994	8
9	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.32000000000000028	9
10	Simple	Desayuno y cena incluidas	Breakfast and dinner included	11.8800000000000008	10
11	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.41999999999999993	11
12	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.41999999999999993	12
13	Simple	Desayuno y cena incluidas	Breakfast and dinner included	4.86000000000000032	13
14	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.01000000000000001	14
15	Simple	Desayuno y cena incluidas	Breakfast and dinner included	3.18000000000000016	15
16	Simple	Desayuno y cena incluidas	Breakfast and dinner included	4.41000000000000014	16
17	Simple	Desayuno y cena incluidas	Breakfast and dinner included	2.56000000000000005	17
18	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.38999999999999968	18
19	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.5	19
20	Simple	Desayuno y cena incluidas	Breakfast and dinner included	6.05999999999999961	20
21	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.4299999999999997	21
22	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.4499999999999993	22
23	Simple	Desayuno y cena incluidas	Breakfast and dinner included	12.3000000000000007	23
24	Simple	Desayuno y cena incluidas	Breakfast and dinner included	4.71999999999999975	24
25	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.56000000000000005	25
26	Simple	Desayuno y cena incluidas	Breakfast and dinner included	11.5899999999999999	26
27	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.49000000000000021	27
28	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.62999999999999989	28
29	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.69999999999999996	29
30	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.5299999999999994	30
31	Simple	Desayuno y cena incluidas	Breakfast and dinner included	12.3300000000000001	31
32	Simple	Desayuno y cena incluidas	Breakfast and dinner included	12.8100000000000005	32
33	Simple	Desayuno y cena incluidas	Breakfast and dinner included	9.50999999999999979	33
34	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.88999999999999968	34
35	Simple	Desayuno y cena incluidas	Breakfast and dinner included	12.3200000000000003	35
36	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.21999999999999975	36
37	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.58000000000000007	37
38	Simple	Desayuno y cena incluidas	Breakfast and dinner included	3.20999999999999996	38
39	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.75999999999999979	39
40	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.20999999999999996	40
41	Simple	Desayuno y cena incluidas	Breakfast and dinner included	2.56999999999999984	41
42	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.37999999999999989	42
43	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.58999999999999986	43
44	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.6099999999999994	44
45	Simple	Desayuno y cena incluidas	Breakfast and dinner included	3.37999999999999989	45
46	Simple	Desayuno y cena incluidas	Breakfast and dinner included	7.62999999999999989	46
48	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.6500000000000004	48
49	Simple	Desayuno y cena incluidas	Breakfast and dinner included	4.40000000000000036	49
50	Simple	Desayuno y cena incluidas	Breakfast and dinner included	3.49000000000000021	50
51	Simple	Desayuno y cena incluidas	Breakfast and dinner included	9.76999999999999957	51
52	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.75	52
53	Simple	Desayuno y cena incluidas	Breakfast and dinner included	1.91999999999999993	53
54	Simple	Desayuno y cena incluidas	Breakfast and dinner included	12.0999999999999996	54
55	Simple	Desayuno y cena incluidas	Breakfast and dinner included	10.1899999999999995	55
56	Simple	Desayuno y cena incluidas	Breakfast and dinner included	11.0800000000000001	56
57	Simple	Desayuno y cena incluidas	Breakfast and dinner included	4.66000000000000014	57
58	Simple	Desayuno y cena incluidas	Breakfast and dinner included	8.42999999999999972	58
59	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	41.3599999999999994	1
60	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	24.1099999999999994	2
61	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	23.75	3
62	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	40.7199999999999989	4
63	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	15.4800000000000004	5
64	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	24.1900000000000013	6
65	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	21.6999999999999993	7
66	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	18.120000000000001	8
67	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	12.9000000000000004	9
68	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	12.4199999999999999	10
69	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	20.0100000000000016	11
70	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	13.4900000000000002	12
71	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	15.4100000000000001	13
72	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	36.75	14
73	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	29.0199999999999996	15
74	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	22.25	16
75	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	41.6700000000000017	17
76	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	38.7100000000000009	18
77	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	17.8299999999999983	19
78	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	22.9400000000000013	20
79	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	40.2999999999999972	21
80	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	16.6700000000000017	22
81	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	38.8500000000000014	23
82	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	19.5	24
83	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	26.9200000000000017	25
84	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	32.6899999999999977	26
85	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	35.6300000000000026	27
86	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	34.0799999999999983	28
87	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	12.7699999999999996	29
88	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	31.6799999999999997	30
89	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	26.1400000000000006	31
90	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	26.1600000000000001	32
91	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	16.5100000000000016	33
92	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	44.3400000000000034	34
93	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	25.5100000000000016	35
94	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	36.9500000000000028	36
95	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	17.1999999999999993	37
96	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	33.9299999999999997	38
97	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	16.2800000000000011	39
98	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	44.6599999999999966	40
99	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	29.8500000000000014	41
100	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	37.759999999999998	42
101	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	33.0600000000000023	43
102	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	34.6899999999999977	44
103	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	26.6999999999999993	45
104	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	31.370000000000001	46
106	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	27.1099999999999994	48
107	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	35.7999999999999972	49
108	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	43.7199999999999989	50
109	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	22.2100000000000009	51
110	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	35.6099999999999994	52
111	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	39.4699999999999989	53
112	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	12.5199999999999996	54
113	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	25.9100000000000001	55
114	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	43.2000000000000028	56
115	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	37.4200000000000017	57
116	Gourmet	Desayuno de lujo incluido	Exotic breakfast Included	22.7899999999999991	58
117	Buffet	Todo lo que puedas comer siempre	always	19.8900000000000006	1
118	Buffet	Todo lo que puedas comer siempre	always	12.2899999999999991	2
119	Buffet	Todo lo que puedas comer siempre	always	14.75	3
120	Buffet	Todo lo que puedas comer siempre	all you can eat	16.8500000000000014	4
121	Buffet	Todo lo que puedas comer siempre	all you can eat	14.2799999999999994	5
122	Buffet	Todo lo que puedas comer siempre	always	18.6099999999999994	6
123	Buffet	Todo lo que puedas comer siempre	all you can eat	18.5399999999999991	7
124	Buffet	Todo lo que puedas comer siempre	always	17.6700000000000017	8
125	Buffet	Todo lo que puedas comer siempre	always	17.1400000000000006	9
126	Buffet	Todo lo que puedas comer siempre	all you can eat	15.7699999999999996	10
127	Buffet	Todo lo que puedas comer siempre	all you can eat	15.1400000000000006	11
128	Buffet	Todo lo que puedas comer siempre	always	19.9600000000000009	12
129	Buffet	Todo lo que puedas comer siempre	always	16.9299999999999997	13
130	Buffet	Todo lo que puedas comer siempre	all you can eat	19.2699999999999996	14
131	Buffet	Todo lo que puedas comer siempre	always	18.7699999999999996	15
132	Buffet	Todo lo que puedas comer siempre	all you can eat	18.7300000000000004	16
133	Buffet	Todo lo que puedas comer siempre	all you can eat	17.3000000000000007	17
134	Buffet	Todo lo que puedas comer siempre	always	13.6500000000000004	18
135	Buffet	Todo lo que puedas comer siempre	all you can eat	19.1000000000000014	19
136	Buffet	Todo lo que puedas comer siempre	always	12.75	20
137	Buffet	Todo lo que puedas comer siempre	always	13.3900000000000006	21
138	Buffet	Todo lo que puedas comer siempre	all you can eat	19.1600000000000001	22
139	Buffet	Todo lo que puedas comer siempre	all you can eat	14.4499999999999993	23
140	Buffet	Todo lo que puedas comer siempre	always	19.0599999999999987	24
141	Buffet	Todo lo que puedas comer siempre	all you can eat	15.25	25
142	Buffet	Todo lo que puedas comer siempre	always	20.0300000000000011	26
143	Buffet	Todo lo que puedas comer siempre	always	20.2600000000000016	27
144	Buffet	Todo lo que puedas comer siempre	all you can eat	19.9100000000000001	28
145	Buffet	Todo lo que puedas comer siempre	always	12.0600000000000005	29
146	Buffet	Todo lo que puedas comer siempre	always	17.5300000000000011	30
147	Buffet	Todo lo que puedas comer siempre	all you can eat	12.4499999999999993	31
148	Buffet	Todo lo que puedas comer siempre	always	12.4600000000000009	32
149	Buffet	Todo lo que puedas comer siempre	always	12.3000000000000007	33
150	Buffet	Todo lo que puedas comer siempre	all you can eat	20.1600000000000001	34
151	Buffet	Todo lo que puedas comer siempre	always	15.0700000000000003	35
152	Buffet	Todo lo que puedas comer siempre	all you can eat	17.0599999999999987	36
153	Buffet	Todo lo que puedas comer siempre	always	12.8699999999999992	37
154	Buffet	Todo lo que puedas comer siempre	all you can eat	12.0899999999999999	38
155	Buffet	Todo lo que puedas comer siempre	all you can eat	12.3599999999999994	39
156	Buffet	Todo lo que puedas comer siempre	all you can eat	19.6799999999999997	40
157	Buffet	Todo lo que puedas comer siempre	always	19.620000000000001	41
158	Buffet	Todo lo que puedas comer siempre	always	14.7300000000000004	42
159	Buffet	Todo lo que puedas comer siempre	always	15.2200000000000006	43
160	Buffet	Todo lo que puedas comer siempre	always	15.4399999999999995	44
161	Buffet	Todo lo que puedas comer siempre	all you can eat	14.7100000000000009	45
162	Buffet	Todo lo que puedas comer siempre	all you can eat	18.3900000000000006	46
164	Buffet	Todo lo que puedas comer siempre	all you can eat	14.9499999999999993	48
165	Buffet	Todo lo que puedas comer siempre	all you can eat	12.2400000000000002	49
166	Buffet	Todo lo que puedas comer siempre	always	15.7699999999999996	50
167	Buffet	Todo lo que puedas comer siempre	all you can eat	15.3200000000000003	51
168	Buffet	Todo lo que puedas comer siempre	always	18.5199999999999996	52
169	Buffet	Todo lo que puedas comer siempre	always	18.0300000000000011	53
170	Buffet	Todo lo que puedas comer siempre	always	14.3499999999999996	54
171	Buffet	Todo lo que puedas comer siempre	all you can eat	16.629999999999999	55
172	Buffet	Todo lo que puedas comer siempre	always	14.9800000000000004	56
173	Buffet	Todo lo que puedas comer siempre	always	14.3699999999999992	57
174	Buffet	Todo lo que puedas comer siempre	always	18.1400000000000006	58
\.


--
-- Name: plancomida_codplan_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('plancomida_codplan_seq', 174, true);


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY reserva (codreserva, fechainicior, fechafinr, numadultos, numninnos, idusuario, idestado, codtransporte, idhotel) FROM stdin;
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

COPY transporte (codtransporte, modelotransporte, numpasajeros, prectransporte, estadoreserva, idhotel) FROM stdin;
101	Toyota	4	235.460000000000008	0	2
102	Mitsubishi	2	357.160000000000025	0	3
103	Hummer	6	289.589999999999975	0	4
104	Yamaha	1	135.930000000000007	0	5
105	Crhysler	4	188.969999999999999	0	6
106	Toyota	4	158.02000000000001	0	7
107	Mitsubishi	2	144.080000000000013	0	8
108	Hummer	6	210.75	0	9
109	Yamaha	1	335.5	0	10
110	Crhysler	4	212.080000000000013	0	11
111	Toyota	4	273.230000000000018	0	12
112	Mitsubishi	2	175.340000000000003	0	13
113	Hummer	6	146.360000000000014	0	14
114	Yamaha	1	227.669999999999987	0	15
115	Crhysler	4	232.02000000000001	0	16
116	Toyota	4	160.72999999999999	0	17
117	Mitsubishi	2	372.730000000000018	0	18
118	Hummer	6	225.539999999999992	0	19
119	Yamaha	1	348.279999999999973	0	20
120	Crhysler	4	370.350000000000023	0	21
121	Toyota	4	394.019999999999982	0	22
122	Mitsubishi	2	360.949999999999989	0	23
123	Hummer	6	290.329999999999984	0	24
124	Yamaha	1	190.199999999999989	0	25
125	Crhysler	4	361.95999999999998	0	26
126	Toyota	4	183.629999999999995	0	27
127	Mitsubishi	2	155.590000000000003	0	28
128	Hummer	6	293.839999999999975	0	29
129	Yamaha	1	154.030000000000001	0	30
130	Crhysler	4	284.95999999999998	0	31
131	Toyota	4	295.120000000000005	0	32
132	Mitsubishi	2	254.189999999999998	0	33
133	Hummer	6	309.660000000000025	0	34
134	Yamaha	1	329.70999999999998	0	35
135	Crhysler	4	195.949999999999989	0	36
136	Toyota	4	322.240000000000009	0	37
137	Mitsubishi	2	355.639999999999986	0	38
138	Hummer	6	233.370000000000005	0	39
139	Yamaha	1	302.180000000000007	0	40
140	Crhysler	4	207.25	0	41
141	Toyota	4	356.389999999999986	0	42
142	Mitsubishi	2	315.850000000000023	0	43
143	Hummer	6	214.740000000000009	0	44
144	Yamaha	1	283.910000000000025	0	45
145	Crhysler	4	267.870000000000005	0	46
147	Mitsubishi	2	380.610000000000014	0	48
148	Hummer	6	276.759999999999991	0	49
149	Yamaha	1	327.860000000000014	0	50
150	Crhysler	4	122.560000000000002	0	51
151	Toyota	4	253.900000000000006	0	52
152	Mitsubishi	2	124.870000000000005	0	53
153	Hummer	6	257.829999999999984	0	54
154	Yamaha	1	164.759999999999991	0	55
155	Crhysler	4	140.47999999999999	0	56
156	Toyota	4	251.370000000000005	0	57
157	Mitsubishi	2	152.590000000000003	0	58
159	Yamaha	1	267.620000000000005	0	2
160	Crhysler	4	393.689999999999998	0	3
161	Toyota	4	189.629999999999995	0	4
162	Mitsubishi	2	267.70999999999998	0	5
163	Hummer	6	136.300000000000011	0	6
164	Yamaha	1	317.699999999999989	0	7
165	Crhysler	4	171.300000000000011	0	8
166	Toyota	4	124.349999999999994	0	9
167	Mitsubishi	2	319.009999999999991	0	10
168	Hummer	6	171.47999999999999	0	11
169	Yamaha	1	153.969999999999999	0	12
170	Crhysler	4	225.919999999999987	0	13
171	Toyota	4	372.490000000000009	0	14
172	Mitsubishi	2	287.410000000000025	0	15
173	Hummer	6	308.220000000000027	0	16
174	Yamaha	1	345.54000000000002	0	17
175	Crhysler	4	132.990000000000009	0	18
176	Toyota	4	328.180000000000007	0	19
177	Mitsubishi	2	330.579999999999984	0	20
178	Hummer	6	215.469999999999999	0	21
179	Yamaha	1	395.860000000000014	0	22
180	Crhysler	4	145.090000000000003	0	23
181	Toyota	4	334.610000000000014	0	24
182	Mitsubishi	2	352.110000000000014	0	25
183	Hummer	6	221.379999999999995	0	26
184	Yamaha	1	204.150000000000006	0	27
185	Crhysler	4	154.180000000000007	0	28
186	Toyota	4	225.069999999999993	0	29
187	Mitsubishi	2	386.470000000000027	0	30
188	Hummer	6	226.289999999999992	0	31
189	Yamaha	1	351.5	0	32
190	Crhysler	4	148	0	33
191	Toyota	4	304.579999999999984	0	34
192	Mitsubishi	2	173.449999999999989	0	35
193	Hummer	6	351.879999999999995	0	36
194	Yamaha	1	282.600000000000023	0	37
195	Crhysler	4	163.77000000000001	0	38
196	Toyota	4	158.740000000000009	0	39
197	Mitsubishi	2	338.319999999999993	0	40
198	Hummer	6	308.139999999999986	0	41
199	Yamaha	1	174.169999999999987	0	42
200	Crhysler	4	369.189999999999998	0	43
201	Toyota	4	359.060000000000002	0	44
202	Mitsubishi	2	363.79000000000002	0	45
203	Hummer	6	293.639999999999986	0	46
205	Crhysler	4	281.970000000000027	0	48
206	Toyota	4	303.649999999999977	0	49
207	Mitsubishi	2	171.930000000000007	0	50
208	Hummer	6	151.969999999999999	0	51
209	Yamaha	1	235.469999999999999	0	52
210	Crhysler	4	221.699999999999989	0	53
211	Toyota	4	215.800000000000011	0	54
212	Mitsubishi	2	138.759999999999991	0	55
213	Hummer	6	198.560000000000002	0	56
214	Yamaha	1	271.79000000000002	0	57
215	Crhysler	4	343.199999999999989	0	58
217	Mitsubishi	2	193.849999999999994	0	2
218	Hummer	6	321.720000000000027	0	3
219	Yamaha	1	179.969999999999999	0	4
220	Crhysler	4	178.379999999999995	0	5
221	Toyota	4	385.920000000000016	0	6
222	Mitsubishi	2	121.340000000000003	0	7
223	Hummer	6	296.610000000000014	0	8
224	Yamaha	1	239.090000000000003	0	9
225	Crhysler	4	301.370000000000005	0	10
226	Toyota	4	349.70999999999998	0	11
227	Mitsubishi	2	313.480000000000018	0	12
228	Hummer	6	177.110000000000014	0	13
229	Yamaha	1	227.340000000000003	0	14
230	Crhysler	4	267.660000000000025	0	15
231	Toyota	4	280.29000000000002	0	16
232	Mitsubishi	2	227.969999999999999	0	17
233	Hummer	6	177.439999999999998	0	18
234	Yamaha	1	149.620000000000005	0	19
235	Crhysler	4	246.219999999999999	0	20
236	Toyota	4	370.589999999999975	0	21
237	Mitsubishi	2	371.800000000000011	0	22
238	Hummer	6	246.370000000000005	0	23
239	Yamaha	1	309.079999999999984	0	24
240	Crhysler	4	178.360000000000014	0	25
241	Toyota	4	334.589999999999975	0	26
242	Mitsubishi	2	217.740000000000009	0	27
243	Hummer	6	130.460000000000008	0	28
244	Yamaha	1	365.870000000000005	0	29
245	Crhysler	4	272.980000000000018	0	30
246	Toyota	4	334.240000000000009	0	31
247	Mitsubishi	2	198.430000000000007	0	32
248	Hummer	6	134.430000000000007	0	33
249	Yamaha	1	298.649999999999977	0	34
250	Crhysler	4	340.509999999999991	0	35
251	Toyota	4	228.860000000000014	0	36
252	Mitsubishi	2	128.52000000000001	0	37
253	Hummer	6	177.060000000000002	0	38
254	Yamaha	1	349.199999999999989	0	39
255	Crhysler	4	348.740000000000009	0	40
256	Toyota	4	252.460000000000008	0	41
257	Mitsubishi	2	247.930000000000007	0	42
258	Hummer	6	266.829999999999984	0	43
259	Yamaha	1	389.850000000000023	0	44
260	Crhysler	4	376.79000000000002	0	45
261	Toyota	4	194.669999999999987	0	46
263	Hummer	6	191.47999999999999	0	48
264	Yamaha	1	336.970000000000027	0	49
265	Crhysler	4	329.189999999999998	0	50
266	Toyota	4	247.719999999999999	0	51
267	Mitsubishi	2	349.310000000000002	0	52
268	Hummer	6	362.329999999999984	0	53
269	Yamaha	1	347.779999999999973	0	54
270	Crhysler	4	141.120000000000005	0	55
271	Toyota	4	314.70999999999998	0	56
272	Mitsubishi	2	365.139999999999986	0	57
273	Hummer	6	316.579999999999984	0	58
275	Crhysler	4	131.030000000000001	0	2
276	Toyota	4	286.009999999999991	0	3
277	Mitsubishi	2	240.840000000000003	0	4
278	Hummer	6	195.340000000000003	0	5
279	Yamaha	1	377.189999999999998	0	6
280	Crhysler	4	212.879999999999995	0	7
281	Toyota	4	268.490000000000009	0	8
282	Mitsubishi	2	361.779999999999973	0	9
283	Hummer	6	177.509999999999991	0	10
284	Yamaha	1	186.400000000000006	0	11
285	Crhysler	4	250.830000000000013	0	12
286	Toyota	4	210	0	13
287	Mitsubishi	2	390.670000000000016	0	14
288	Hummer	6	388.5	0	15
289	Yamaha	1	157.689999999999998	0	16
290	Crhysler	4	376.350000000000023	0	17
291	Toyota	4	289.680000000000007	0	18
292	Mitsubishi	2	389.519999999999982	0	19
293	Hummer	6	126.310000000000002	0	20
294	Yamaha	1	122.969999999999999	0	21
295	Crhysler	4	183.360000000000014	0	22
296	Toyota	4	255.050000000000011	0	23
297	Mitsubishi	2	152.689999999999998	0	24
298	Hummer	6	310.649999999999977	0	25
299	Yamaha	1	138.789999999999992	0	26
300	Crhysler	4	199.159999999999997	0	27
301	Toyota	4	260.75	0	28
302	Mitsubishi	2	193.909999999999997	0	29
303	Hummer	6	321.069999999999993	0	30
304	Yamaha	1	158.569999999999993	0	31
305	Crhysler	4	341.509999999999991	0	32
306	Toyota	4	347.879999999999995	0	33
307	Mitsubishi	2	165.949999999999989	0	34
308	Hummer	6	396	0	35
309	Yamaha	1	229.169999999999987	0	36
310	Crhysler	4	218.340000000000003	0	37
311	Toyota	4	300.899999999999977	0	38
312	Mitsubishi	2	198.219999999999999	0	39
313	Hummer	6	176.310000000000002	0	40
314	Yamaha	1	219.460000000000008	0	41
315	Crhysler	4	145.349999999999994	0	42
316	Toyota	4	380.519999999999982	0	43
317	Mitsubishi	2	386.879999999999995	0	44
318	Hummer	6	212.789999999999992	0	45
319	Yamaha	1	135.139999999999986	0	46
321	Toyota	4	232.680000000000007	0	48
322	Mitsubishi	2	262.079999999999984	0	49
323	Hummer	6	145.629999999999995	0	50
324	Yamaha	1	361.199999999999989	0	51
325	Crhysler	4	325.930000000000007	0	52
326	Toyota	4	388.730000000000018	0	53
327	Mitsubishi	2	233.680000000000007	0	54
328	Hummer	6	238.539999999999992	0	55
329	Yamaha	1	310.04000000000002	0	56
330	Crhysler	4	384.980000000000018	0	57
331	Toyota	4	141.439999999999998	0	58
333	Hummer	6	268.910000000000025	0	2
334	Yamaha	1	213.409999999999997	0	3
335	Crhysler	4	264.5	0	4
336	Toyota	4	239.810000000000002	0	5
337	Mitsubishi	2	216.870000000000005	0	6
338	Hummer	6	154.090000000000003	0	7
339	Yamaha	1	362.350000000000023	0	8
340	Crhysler	4	178.039999999999992	0	9
341	Toyota	4	259.189999999999998	0	10
342	Mitsubishi	2	240.280000000000001	0	11
343	Hummer	6	188.819999999999993	0	12
344	Yamaha	1	331.089999999999975	0	13
345	Crhysler	4	313.170000000000016	0	14
346	Toyota	4	370.180000000000007	0	15
347	Mitsubishi	2	371.990000000000009	0	16
348	Hummer	6	211.050000000000011	0	17
349	Yamaha	1	310.79000000000002	0	18
350	Crhysler	4	128.77000000000001	0	19
351	Toyota	4	336.379999999999995	0	20
352	Mitsubishi	2	297.319999999999993	0	21
353	Hummer	6	198.02000000000001	0	22
354	Yamaha	1	161.009999999999991	0	23
355	Crhysler	4	293.850000000000023	0	24
356	Toyota	4	354.970000000000027	0	25
357	Mitsubishi	2	321.04000000000002	0	26
358	Hummer	6	302.610000000000014	0	27
359	Yamaha	1	290.769999999999982	0	28
360	Crhysler	4	313.370000000000005	0	29
361	Toyota	4	359.779999999999973	0	30
362	Mitsubishi	2	321.490000000000009	0	31
363	Hummer	6	352.389999999999986	0	32
364	Yamaha	1	252.560000000000002	0	33
365	Crhysler	4	225.659999999999997	0	34
366	Toyota	4	183.370000000000005	0	35
367	Mitsubishi	2	392.930000000000007	0	36
368	Hummer	6	331.029999999999973	0	37
369	Yamaha	1	321.800000000000011	0	38
370	Crhysler	4	228.909999999999997	0	39
371	Toyota	4	141.379999999999995	0	40
372	Mitsubishi	2	394.350000000000023	0	41
373	Hummer	6	262.670000000000016	0	42
374	Yamaha	1	242.199999999999989	0	43
375	Crhysler	4	279.970000000000027	0	44
376	Toyota	4	367.870000000000005	0	45
377	Mitsubishi	2	187.22999999999999	0	46
379	Yamaha	1	375.649999999999977	0	48
380	Crhysler	4	239.159999999999997	0	49
381	Toyota	4	360.629999999999995	0	50
382	Mitsubishi	2	286.29000000000002	0	51
383	Hummer	6	250.830000000000013	0	52
384	Yamaha	1	213.27000000000001	0	53
385	Crhysler	4	385.129999999999995	0	54
386	Toyota	4	386.930000000000007	0	55
387	Mitsubishi	2	327.54000000000002	0	56
388	Hummer	6	283.850000000000023	0	57
389	Yamaha	1	184.659999999999997	0	58
\.


--
-- Name: transporte_codtransporte_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('transporte_codtransporte_seq', 390, true);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: fernando
--

COPY usuario (idusuario, nomusuario, apeusuario, emailusuario, passusuario, cnxusuario, rol) FROM stdin;
1	root	admin	root@admin.com	63a9f0ea7bb98050796b649e85481845	f	2
\.


--
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: fernando
--

SELECT pg_catalog.setval('usuario_idusuario_seq', 1, true);


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

