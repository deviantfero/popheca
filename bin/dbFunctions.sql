create function register( name varchar(20), lasna varchar(20), email varchar(30), pass varchar(100)) returns void as '
	insert into usuario ( nomUsuario, apeUsuario, emailUsuario, passUsuario, cnxUsuario, rol )
	values( name, lasna, email, pass, false, 1 );
' language sql;
