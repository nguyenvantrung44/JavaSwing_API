////
mỗi entity có active =>(đã xóa hay chưa xóa) , 
updateAt,createAt = => viet trong abstractentity

cứ mỗi entity mà có thể xóa hoặc update được thì cứ extent cái abstractentity



repository:
    -thu_muc(query)
        +interface(vd: DiaQuery)
        +impl:
            +classImpl(vd: DiaQueryImpl )=>impl interface query
    -Interface(vd : DiaRepository)
    