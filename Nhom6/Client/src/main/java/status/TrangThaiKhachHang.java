package status;

public enum TrangThaiKhachHang {
	dangHoatDong{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Đang hoạt động";
		}
	},
	ngungHoatDong{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Ngưng hoạt động";
		}
	}
}
