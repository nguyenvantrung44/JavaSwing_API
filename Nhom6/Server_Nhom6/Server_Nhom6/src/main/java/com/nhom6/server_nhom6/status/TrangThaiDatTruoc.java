package com.nhom6.server_nhom6.status;

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
	},
	huy{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Hủy";
		}
	}
}
