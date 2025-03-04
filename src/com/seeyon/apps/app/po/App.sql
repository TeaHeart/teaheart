drop table if exists app;
create table app
(
    id    bigint primary key  not null,
    [key] varchar(255) unique not null,
    value varchar(255)        not null
);
go;

drop proc if exists add_data;
go;
create proc add_data(@count int) as
begin
    declare @i bigint;
    set @i = 1;
    while (@i <= @count)
        begin
            insert into app ([key], value, id)
            values (cast(@i as varchar(255)), cast(@i * 2 as varchar(255)), rand() * 2147483647);
            set @i = @i + 1;
        end
end;
go;

exec add_data 40;
select *
from app
order by cast([key] as bigint);
go;