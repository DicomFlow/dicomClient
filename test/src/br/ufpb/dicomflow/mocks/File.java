/*
 * 	This file is part of DicomFlow.
 * 
 * 	DicomFlow is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 * 
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 * 
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package br.ufpb.dicomflow.mocks;

import java.util.Date;

public class File  {

	
	private static final long serialVersionUID = 202100461015580129L;
	
	private Long id;
	
	private Instance instance;
	
	private Date createdTime;
	
	private Date timeOfLastMd5Check;
	
    private String filePath;
	
    private String fileTsuid;
	
    private String fileMd5Field;
	
    private Integer fileStatus;
	
    private Long fileSize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getTimeOfLastMd5Check() {
		return timeOfLastMd5Check;
	}

	public void setTimeOfLastMd5Check(Date timeOfLastMd5Check) {
		this.timeOfLastMd5Check = timeOfLastMd5Check;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileTsuid() {
		return fileTsuid;
	}

	public void setFileTsuid(String fileTsuid) {
		this.fileTsuid = fileTsuid;
	}

	public String getFileMd5Field() {
		return fileMd5Field;
	}

	public void setFileMd5Field(String fileMd5Field) {
		this.fileMd5Field = fileMd5Field;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}	    
    
}
