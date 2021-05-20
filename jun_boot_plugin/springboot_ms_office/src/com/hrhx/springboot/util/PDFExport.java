package com.hrhx.springboot.util;



public class PDFExport implements ExportInterface{
	private OfficeExport officeExport;
	
	public PDFExport(OfficeExport officeExport) {
		super();
		this.officeExport = officeExport; 
	}

	@Override
	public void export() {
		System.out.println(officeExport);
	}
}
















