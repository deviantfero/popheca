create or replace function register( name varchar(20), lasna varchar(20), email varchar(30), pass varchar(100)) returns void as '
	insert into usuario ( nomUsuario, apeUsuario, emailUsuario, passUsuario, cnxUsuario, rol )
	values( name, lasna, email, pass, false, 1 );
' language sql;

create or replace function makereserve( fechaini date, fechafin date, numa int, numn int, idfac int, idu int, ide int, idh int ) returns void as '
	insert into reserva (fechainicior, fechafinr, numadultos, numninnos, idfactura, idusuario, idestado, idhotel)
	values( fechaini, fechafin, numa, numn, idfac, idu, ide, idh );
' language sql;

create or replace function getroom_reserve(numhabitacion int) returns refcursor as $$
declare
	ref refcursor;
begin
	open ref for select X.codhabitacion, X.codreserva, R.fechainicior,
	R.fechafinr from reservaxhabitacion as X, reserva as R
	where X.codreserva=R.codreserva and X.codhabitacion=numhabitacion;
	return ref;
end;
$$ language plpgsql;
--will be useful later
