package status;

public enum TrangThaiPhiTreHen {
	daThanhToanXong{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Thanh toán xong";
		}
	},
	chuaThanhToanHet{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return"Chưa thanh toán xong";
		}
	}
}
