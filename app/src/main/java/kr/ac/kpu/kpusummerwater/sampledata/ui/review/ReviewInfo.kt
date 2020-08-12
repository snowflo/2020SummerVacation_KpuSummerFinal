package kr.ac.kpu.kpusummerwater.sampledata.ui.review

class ReviewInfo{
    //val id:String, val title:String, val maker:String, val date:String)
    //게시물 번호
    //var id:String? = null
    var title: String? = null
    var maker: String? = null
    var date: String? = null

    constructor(){}

    constructor(title:String?, maker:String?, date:String?){
        this.title = title
        this.maker = maker
        this.date = date
    }
}