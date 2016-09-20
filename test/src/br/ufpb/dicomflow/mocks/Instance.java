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
import java.util.Set;

public class Instance {

	private static final long serialVersionUID = 5851474929556502906L;

	private Long id;
	
	private Series series;
		
	private Set<File> files;
	
	private String sopIuid;
	
	private String sopCuid;
	
	private String instanceNumber;
	
	private Date contentDateTime;
	
	private String retrieveAETs;
	
    private Integer availability;
	
    private Integer instanceStatus;
	
    private Boolean archived;
	
    private Boolean allAttributes;
	
    private Boolean commitment;
	
    private Date updatedTime;
	
    private Date createdTime;
	
    private byte[] encodedAttributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public String getSopIuid() {
		return sopIuid;
	}

	public void setSopIuid(String sopIuid) {
		this.sopIuid = sopIuid;
	}

	public String getSopCuid() {
		return sopCuid;
	}

	public void setSopCuid(String sopCuid) {
		this.sopCuid = sopCuid;
	}

	public String getInstanceNumber() {
		return instanceNumber;
	}

	public void setInstanceNumber(String instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

	public Date getContentDateTime() {
		return contentDateTime;
	}

	public void setContentDateTime(Date contentDateTime) {
		this.contentDateTime = contentDateTime;
	}

	public String getRetrieveAETs() {
		return retrieveAETs;
	}

	public void setRetrieveAETs(String retrieveAETs) {
		this.retrieveAETs = retrieveAETs;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Integer getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(Integer instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public Boolean getAllAttributes() {
		return allAttributes;
	}

	public void setAllAttributes(Boolean allAttributes) {
		this.allAttributes = allAttributes;
	}

	public Boolean getCommitment() {
		return commitment;
	}

	public void setCommitment(Boolean commitment) {
		this.commitment = commitment;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public byte[] getEncodedAttributes() {
		return encodedAttributes;
	}

	public void setEncodedAttributes(byte[] encodedAttributes) {
		this.encodedAttributes = encodedAttributes;
	}			

}
