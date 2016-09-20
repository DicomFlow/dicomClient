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

public class Series {
	
	private static final long serialVersionUID = 6640652465765376582L;
	
	private Long id;
	
	private Study study;

	private Set<Instance> instances;
	
	private String seriesIuid;
	
	private String seriesNumber;
	
	private String modality;
	
	private String bodyPartExamined;
	
	private Date ppsStartDate;
	
	private String ppsIuid;
	
	private Integer numberOfSeriesRelatedInstances;
	
	private String sourceAET;
	
	private String retrieveAETs;
	
    private Integer availability;
	
    private Integer seriesStatus;
	
    private Date updatedTime;
	
    private Date createdTime;
	
    private byte[] encodedAttributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public Set<Instance> getInstances() {
		return instances;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public String getSeriesIuid() {
		return seriesIuid;
	}

	public void setSeriesIuid(String seriesIuid) {
		this.seriesIuid = seriesIuid;
	}

	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getBodyPartExamined() {
		return bodyPartExamined;
	}

	public void setBodyPartExamined(String bodyPartExamined) {
		this.bodyPartExamined = bodyPartExamined;
	}

	public Date getPpsStartDate() {
		return ppsStartDate;
	}

	public void setPpsStartDate(Date ppsStartDate) {
		this.ppsStartDate = ppsStartDate;
	}

	public String getPpsIuid() {
		return ppsIuid;
	}

	public void setPpsIuid(String ppsIuid) {
		this.ppsIuid = ppsIuid;
	}

	public Integer getNumberOfSeriesRelatedInstances() {
		return numberOfSeriesRelatedInstances;
	}

	public void setNumberOfSeriesRelatedInstances(Integer numberOfSeriesRelatedInstances) {
		this.numberOfSeriesRelatedInstances = numberOfSeriesRelatedInstances;
	}

	public String getSourceAET() {
		return sourceAET;
	}

	public void setSourceAET(String sourceAET) {
		this.sourceAET = sourceAET;
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

	public Integer getSeriesStatus() {
		return seriesStatus;
	}

	public void setSeriesStatus(Integer seriesStatus) {
		this.seriesStatus = seriesStatus;
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
