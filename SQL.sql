CREATE TABLE public.cliente
(
    id_cliente integer NOT NULL,
    nombre character varying(50) NOT NULL,
    apellido character varying(50) NOT NULL,
    nro_documento integer NOT NULL,
    tipo_documento character varying(50) NOT NULL,
    nacionalidad character varying(30) NOT NULL,
    email character varying(30) NOT NULL,
    telefono character varying(30) NOT NULL,
    fecha_nacimiento date NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente)
);
CREATE SEQUENCE public.ckiente_sec;
CREATE TABLE public.bolsa_de_puntos
(
    id_bolsa_de_puntos integer NOT NULL,
    id_cliente integer NOT NULL,
    fecha_asignacion_puntaje date NOT NULL,
    fecha_caducidad_puntaje date NOT NULL,
    puntaje_asignado integer NOT NULL,
    puntaje_utilizado integer NOT NULL,
    saldo_puntos integer NOT NULL,
    monto_operacion integer NOT NULL,
    usuario character varying (40),
    sello_tiempo timestamp,
    CONSTRAINT bolsa_de_puntos_pkey PRIMARY KEY (id_bolsa_de_puntos),
    CONSTRAINT fk_1 FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.bolsa_de_puntos_sec;
CREATE TABLE public.concepto_uso_puntos
(
    id_concepto_uso_puntos integer NOT NULL,
    descripcion character varying(50) NOT NULL,
    puntos_requeridos integer NOT NULL,
    CONSTRAINT concepto_uso_puntos_pkey PRIMARY KEY (id_concepto_uso_puntos)

);
CREATE SEQUENCE public.concepto_uso_puntos_sec;
CREATE TABLE public.uso_puntos
(
    id_uso_puntos integer NOT NULL,
    id_cliente integer NOT NULL,
    puntos_utilizados integer NOT NULL,
	fecha date NOT NULL,
	id_concepto_uso_puntos integer NOT NULL,
	usuario character varying (40),
    sello_tiempo timestamp,
    CONSTRAINT uso_punto_pkey PRIMARY KEY (id_uso_puntos),
	CONSTRAINT fk_2 FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_3 FOREIGN KEY (id_concepto_uso_puntos)
        REFERENCES public.concepto_uso_puntos (id_concepto_uso_puntos) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.uso_puntos_sec;
CREATE TABLE public.detalle_uso_puntos
(
    id_detalle_uso_puntos integer NOT NULL,
    id_uso_puntos integer NOT NULL,
    puntaje_usado integer NOT NULL,
    id_bolsa integer NOT NULL,
    CONSTRAINT detalle_uso_puntos_pkey PRIMARY KEY (id_detalle_uso_puntos),
    CONSTRAINT fk_4 FOREIGN KEY (id_uso_puntos)
        REFERENCES public.uso_puntos (id_uso_puntos) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    CONSTRAINT fk_5 FOREIGN KEY (id_bolsa_de_puntos)
        REFERENCES public.bolsa_de_puntos (id_bolsa_de_puntos) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.detalle_uso_puntos_sec;
CREATE TABLE public.vencimiento_puntos
(
    id_vencimiento_puntos INTEGER PRIMARY KEY,
    inicio_validez DATE NOT NULL,
    fin_validez DATE NOT NULL,
    dias_duracion INTEGER NOT NULL
);
CREATE SEQUENCE public.vencimiento_puntos_sec;

CREATE TABLE public.regla_asignacion
(
  id_regla_asignacion integer NOT NULL,
  hasta integer NOT NULL,
  desde integer NOT NULL,
  valor integer NOT NULL,
  CONSTRAINT regla_asignacion_pkey PRIMARY KEY (id_regla_asignacion)
);
CREATE SEQUENCE public.regla_asignacion_sec;