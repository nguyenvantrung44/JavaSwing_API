package com.nhom6.server_nhom6.status;

public enum TrangThaiDia {
	sanSangChoThue{
		@Override
		public String toString() {
			return "Sẵn sàng cho thuê";
		}
	},
	daThue{
		@Override
		public String toString() {
			return"Đã thuê";
		}
	},
	daGan{
		@Override
		public String toString() {
			return "Đã Gán";
		}
	},
	ngungChoThue{
		@Override
		public String toString() {
			return "Ngưng Cho Thuê";
		}
	}
}
