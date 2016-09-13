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

public class Patient {
	
	private Long id;
	
	private Set<Study> studies;
		
    private String patientId;
	
    private String patientName;
	
    private String patientFamilyNameSoundex;
	
    private String patientGivenNameSoundex;
	
    private String patientIdeographicName;
	
    private String patientPhoneticName;
	
    private String patientBirthDate;
	
    private String patientSex;
	
    private Date updatedTime;
		
    private Date createdTime;
	
    private byte[] encodedAttributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientFamilyNameSoundex() {
		return patientFamilyNameSoundex;
	}

	public void setPatientFamilyNameSoundex(String patientFamilyNameSoundex) {
		this.patientFamilyNameSoundex = patientFamilyNameSoundex;
	}

	public String getPatientGivenNameSoundex() {
		return patientGivenNameSoundex;
	}

	public void setPatientGivenNameSoundex(String patientGivenNameSoundex) {
		this.patientGivenNameSoundex = patientGivenNameSoundex;
	}

	public String getPatientIdeographicName() {
		return patientIdeographicName;
	}

	public void setPatientIdeographicName(String patientIdeographicName) {
		this.patientIdeographicName = patientIdeographicName;
	}

	public String getPatientPhoneticName() {
		return patientPhoneticName;
	}

	public void setPatientPhoneticName(String patientPhoneticName) {
		this.patientPhoneticName = patientPhoneticName;
	}

	public String getPatientBirthDate() {
		return patientBirthDate;
	}

	public void setPatientBirthDate(String patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
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
