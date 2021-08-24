package status;

public enum TrangThaiDatTruoc {
	xacNhan{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Xác nhận";
		}
	},
	chuaXacNhan{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Chưa xác nhận";
		}
	}
}
