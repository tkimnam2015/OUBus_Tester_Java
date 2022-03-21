create database OUBus1
go
use OUBus1
go
create table ChuyenDi(
	maChuyen int not null identity(1, 1),
	tenChuyen nvarchar(255) not null,
	diemKhoiHanh nvarchar(255),
	diemDen nvarchar(255),
	ngayDi date,
	gioDi time(0),
	giaTien decimal(10, 0),
	primary key(maChuyen)
)
go

insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Đồng Tháp', N'Sài Gòn', N'Đồng Tháp', '2022-01-20', '05:00:00', '100000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Tây Ninh', N'Sài Gòn', N'Tây Ninh', '2022-01-22', '06:00:00', '100000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Trà Vinh', N'Sài Gòn', N'Trà Vinh', '2022-01-22', '07:00:00', '150000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Long An', N'Sài Gòn', N'Long An', '2022-01-23', '08:00:00', '100000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Vĩnh Long', N'Sài Gòn', N'Vĩnh Long', '2022-01-24', '09:00:00', '200000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Lâm Đồng', N'Sài Gòn', N'Lâm Đồng', '2022-01-23', '22:00', '300000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Cà Mau', N'Sài Gòn', N'Cà Mau', '2022-01-25', '10:00', '200000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Bến Tre', N'Sài Gòn', N'Bến Tre', '2022-01-25', '08:00', '100000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Sóc Trăng', N'Sài Gòn', N'Sóc Trăng', '2022-01-26', '10:00', '150000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Vũng Tàu', N'Sài Gòn', N'Vũng Tàu', '2022-01-25', '10:00', '200000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sài Gòn - Hà Nội', N'Sài Gòn', N'Hà Nội', '2022-01-25', '10:00', '600000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Vũng Tàu - Sài Gòn', N'Vũng Tàu', N'Sài Gòn', '2022-02-14', '10:00', '200000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Sóc Trăng - Sài Gòn', N'Sóc Trăng', N'Sài Gòn', '2022-02-10', '10:00', '150000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Bến Tre - Sài Gòn', N'Bến Tre', N'Sài Gòn', '2022-02-10', '08:00', '100000')
insert into ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) values (N'Cà Mau - Sài Gòn', N'Cà Mau', N'Sài Gòn', '2022-02-10', '10:00', '200000')

create table ChucVu(
	maChucVu int not null identity(1, 1),
	tenChucVu nvarchar(100),
	primary key(maChucVu)
)
go

insert into ChucVu(tenChucVu) values ('Staff')
insert into ChucVu(tenChucVu) values ('Manager')

create table NhanVien(
	maNhanVien int not null identity(1, 1),
	hoNhanVien nvarchar(255) not null,
	tenNhanVien nvarchar(100) not null,
	ngaySinh date null,
	gioiTinh nvarchar(100) null,
	diaChi nvarchar(255) null,
	CMND nvarchar(100) null,
	soDienThoai nvarchar(10) null,
	email nvarchar(100) null,
	maChucVu int,
	primary key(maNhanVien),
	constraint fkNhanVien_ChucVu foreign key(maChucVu) references ChucVu(maChucVu)
)
go

insert into NhanVien(hoNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, CMND, soDienThoai, email, maChucVu) values (N'Nguyễn Thị', N'Anh', '1982-02-20', 'Female', N'TP HCM', '072201225987', '0854219785', 'anh@gmail.com', 2)
insert into NhanVien(hoNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, CMND, soDienThoai, email, maChucVu) values (N'Nguyễn Thanh', N'Bình', '1988-02-25', 'Male', N'Long An', '072201226087', '0854219985', 'binh@gmail.com', 1)
insert into NhanVien(hoNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, CMND, soDienThoai, email, maChucVu) values (N'Nguyễn Ngọc', N'Châu', '1990-02-10', 'Female', N'TP HCM', '072601225987', '0861219785', 'chau@gmail.com', 1)
insert into NhanVien(hoNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, CMND, soDienThoai, email, maChucVu) values (N'Nguyễn Văn', N'Dũng', '1976-02-06', 'Male', N'Tây Ninh', '072201445987', '0854819785', 'dung@gmail.com', 2)
insert into NhanVien(hoNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, CMND, soDienThoai, email, maChucVu) values (N'Nguyễn Thu', N'Hồng', '1989-02-20', 'Female', N'TP HCM', '072202625987', '0854009785', 'hong@gmail.com', 1)

create table TaiKhoan(
	maTaiKhoan int not null identity(1, 1),
	tenDangNhap nvarchar(255) not null,
	matKhau nvarchar(255) not null,
	maNhanVien int,
	primary key(maTaiKhoan),
	constraint fkTaiKhoan_NhanVien foreign key(maNhanVien) references NhanVien(maNhanVien)
)
go

insert into TaiKhoan(tenDangNhap, matKhau, maNhanVien) values ('anh@gmail.com', '123', 1)
insert into TaiKhoan(tenDangNhap, matKhau, maNhanVien) values ('binh@gmail.com', '456', 2)
insert into TaiKhoan(tenDangNhap, matKhau, maNhanVien) values ('chau@gmail.com', '789', 3)
insert into TaiKhoan(tenDangNhap, matKhau, maNhanVien) values ('dung@gmail.com', '321', 4)
insert into TaiKhoan(tenDangNhap, matKhau, maNhanVien) values ('hong@gmail.com', '654', 5)

create table Xe(
	maXe int not null identity(1, 1),
	tenXe nvarchar(255) not null,
	bienSoXe nvarchar(10) not null,
	soGhe int,
	primary key(maXe),
)
go

insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.1', '51B-123.64', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.2', '51B-327.11', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.3', '51B-556.02', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.4', '51B-551.65', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.5', '51B-662.74', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.6', '52B-723.65', 16)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.7', '52B-327.55', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.8', '52B-332.25', 16)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.9', '52B-921.84', 45)
insert into Xe(tenXe, bienSoXe, soGhe) values('Bus No.10', '52B-995.33', 16)

create table VeXe(
	maVeXe int not null identity(1, 1),
	tenVeXe nvarchar(255) not null,
	maChuyenDi int,
	maXe int,
	hoKhachHang nvarchar(255) null,
	tenKhachHang nvarchar(100) null,
	gioiTinh nvarchar(100) null,
	diaChi nvarchar(255) null,
	soDienThoai nvarchar(100),
	viTriGheNgoi nvarchar(10) null,
	ghiChu nvarchar(max) null default 'Empty',
	primary key(maVeXe),
	constraint fkVeXe_ChuyenDi foreign key(maChuyenDi) references ChuyenDi(maChuyen),
	constraint fkVeXe_Xe foreign key(maXe) references Xe(maXe)
)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.1', 1, 1)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.2', 1, 2)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.3', 2, 3)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.4', 3, 4)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.5', 4, 5)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.6', 5, 6)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.7', 6, 7)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.8', 7, 8)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.9', 8, 9)
insert into VeXe(tenVeXe, maChuyenDi, maXe) values('Ticket No.10', 9, 10)
