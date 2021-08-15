package com.destkbo.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destkbo.ppmtool.domain.Backlog;
import com.destkbo.ppmtool.domain.Project;
import com.destkbo.ppmtool.domain.User;
import com.destkbo.ppmtool.exceptions.ProjectIdException;
import com.destkbo.ppmtool.exceptions.ProjectNotFoundException;
import com.destkbo.ppmtool.repositories.BacklogRepository;
import com.destkbo.ppmtool.repositories.ProjectRepository;
import com.destkbo.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository ;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userReposiory;
	
	public Project saveOrUpdateProject(Project project, String username){
		
		
		if(project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject != null &&(!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			}else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with id : '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
			}
		}
		
		
		try {
			User user = userReposiory.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
		}catch(Exception e){
			throw new ProjectIdException("Project ID'"+project.getProjectIdentifier().toUpperCase()+"'Already Existe.");
			
		}
		
		
	}
	
	public Project findProjectByIdentifier(String projectId, String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project does not Existe.");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}
		
		
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByprojectLeader(username);
	}
	
	public void deleteProjectByIdentifier(String projectId, String username){
		/*Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project ==null) {
			throw new ProjectIdException("Cannot , Project with'"+projectId+"'This project does not existe");
		}*/
		projectRepository.delete(findProjectByIdentifier(projectId,username));
	}
	
	
	
	
	
}
