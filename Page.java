package entity;

import dao.CommentDao;

public class Page {
	private int page_no = 0;//当前页面的页数
	private int commentNum = 3;//每页显示的数目
	private int totalPage;//总页数
	private int nextStarNum;// 开始的评论的条数
	public Page() {//初始化就获取总的评论数，算出总页数
		int commentsNum = new CommentDao().getCommentNum();
		this.totalPage = commentsNum % commentNum == 0 ? commentsNum
				/ commentNum : commentsNum / commentNum + 1;
	}

	public int getNextStarNum() {
		return nextStarNum;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;//获取页面的同时就算出开始的评论的条数
		this.nextStarNum = page_no * commentNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

}
