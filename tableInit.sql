Create table Usuario(
	idUsuario serial primary key,
	nomUsuario varchar(20) not null,
	apeUsuario varchar(20) not null,
	emailUsuario varchar(30) not null unique,
	passUsuario varchar (100) not null,
	cnxUsuario boolean not null,
	rol int not null
);

create table Reserva(
	codReserva serial,
	fechaInicioR date not null,
	fechaFinR date not null,
	numAdultos int not null,
	numNinnos int,
	idFactura int not null,
	idUsuario int not null,
	idEstado int not null,
	codTransporte varchar(7),
	idHotel int not null
);
alter table Reserva add primary key (codReserva);
alter table Reserva add foreign key (idUsuario) references Usuario on delete cascade;

create table Entrada(
	codEntrada serial primary key,
	tipoEntrada varchar (50) not null,
	precioEntrada float not null,
	idEstado int not null
);

create table ReservaXEntrada(
	codReserva int not null,
	codEntrada int not null,
	cantidad int not null,
	fecha date not null
);

alter table ReservaXEntrada add primary key (codReserva, codEntrada);
alter table ReservaXEntrada add foreign key (codReserva) references Reserva on delete cascade;
alter table ReservaXEntrada add foreign key (codEntrada) references Entrada on delete cascade;

create table Estado(
	idEstado serial unique primary key,
	nomEstado varchar(20)
);

alter table Reserva add foreign key (idEstado) references Estado on delete cascade;
alter table Entrada add foreign key (idEstado) references Estado on delete cascade;

create table Hotel(
	idHotel serial primary key,
	nomHotel varchar(50) not null,
	lvlHotel int not null default 0,
	descrHotel varchar(300) not null default 'n/a',
	descrHotelEng varchar(300) not null default 'n/a',
	direcHotel varchar(100) not null default 'n/a',
	idEstado int not null
);

alter table Hotel add foreign key (idEstado) references Estado on delete cascade;
alter table Reserva add foreign key (idHotel) references Hotel on delete cascade;

create table Habitacion (
	codHabitacion serial primary key,
	maxPerson int not null default 1,
	prcHabitacion float not null,
	detHabitacion varchar(250) not null default 'n/a',
	EstadoReserva int not null,
	idHotel int not null
);

alter table Habitacion add foreign key (idHotel) references Hotel on delete cascade;

create table PlanComida(
	codPlan serial primary key,
	nomPlan varchar(20) not null,
	descrPlan varchar(150) not null default 'n/a',
	idHotel int not null
);
alter table PlanComida add foreign key (idHotel) references Hotel on delete cascade;

create table Transporte(
	codTransporte varchar(7) not null primary key,
	modeloTransporte varchar(10) not null,
	numPasajeros int not null default 1,
	capAlmacenaje float not null,
	EstadoReserva int not null,
	idHotel int not null
);

alter table Transporte add foreign key (idHotel) references Hotel on delete cascade;
alter table Reserva add foreign key (codTransporte) references Transporte on delete cascade;

create Table AdminXCliente(
	idAdmin int not null,
	idCliente int not null
);
alter table AdminXCliente add primary key(idAdmin, idCliente);
alter table AdminXCliente add foreign key(idAdmin) references Usuario on delete cascade;
alter table AdminXCliente add foreign key(idCliente) references Usuario on delete cascade;

create table EstadoReserva(
	EstadoReserva int not null primary key,
	estadoActual varchar(15) not null
);

Insert into EstadoReserva values(0, 'Libre');
Insert into EstadoReserva values(1, 'Reservado');
Insert into EstadoReserva values(2, 'Ocupado');
select * from EstadoReserva;

alter table Habitacion add foreign key (EstadoReserva) references EstadoReserva on delete cascade;
alter table Transporte add foreign key (EstadoReserva) references EstadoReserva on delete cascade;

Create Table ReservaXHabitacion (
	codReserva int not null,
	codHabitacion int not null,
	cantTipo int default 1
);

Alter Table ReservaXHabitacion Add Primary Key(codReserva, codHabitacion);
ALter table ReservaXHabitacion add foreign key(codReserva) references Reserva on delete cascade;
Alter table ReservaXHabitacion add foreign key (codHabitacion) references Habitacion on delete cascade;	

Create Table ReservaXPlan (
	codReserva int not null,
	codPlan int,
	cantidad int default 1
);

Alter table ReservaXPlan add primary key (codReserva, codPlan);
ALter table ReservaXPlan add foreign key (codReserva) references Reserva on delete cascade;
Alter table ReservaXPlan add foreign key (codPlan) references PlanComida on delete cascade;


--drop table ReservaXPlan;
--drop table ReservaXHabitacion;
--drop table ReservaXEntrada;
--drop table Reserva;
--drop table Usuario;
--drop table Transporte;
--drop table Habitacion;
--drop table PlanComida;
--drop table Entrada;
--drop table Estado;
--drop table Hotel;

insert into Usuario ( nomUsuario, apeUsuario, emailUsuario, passUsuario, cnxUsuario, rol ) values( 'root', 'admin', 'root@admin.com','63a9f0ea7bb98050796b649e85481845',false, 0 );
