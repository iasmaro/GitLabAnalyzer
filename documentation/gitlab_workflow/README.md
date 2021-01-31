# Important note   

NEVER modify the master branch directly, always add changes in a branch, merge the most recent master into your branch, resolve conflicts, before merge the branch back the master.  

# GitLab Workflow

1. Create an issue for the feature/bug you want to track
2. Create a branch (either locally or through GitLab) for the issue you created (or any issue that no one is 
   currently working on and you want to start working on it)
    - Branch naming convention: `issueID-issue-title`, for example, if you create a branch to resolve issue 
      number 9, whose title is  
    "lift component up", name your branch as "9-lift-component-up"
      
3. Working in your branch as you normal do: write code, add, commit, push
4. When you are ready to submit a merge request:
    - Locally:
        - checkout branch master
        - pull to get all changes made to master while you were working your own branch
        - check out your branch
        - merge branch master into your branch, resolve all conflicts 
    - GitLab:
        - Create a merge request
            - Fist click on the Merge Requests tab and then click on the New merge request button, you will see 
          something similar to this:
            - Select the source branch and target branch from the dropdown and then click on the `Compare branches 
              and continue`:
              
               <img src="images/sc1.png" width="90%">
          
            - Config you merge request: 
           
               <img src="images/sc2.png" width="90%">
               
               - See that `Closes #1` in the description? That's because the branch name in this case start with
          number 1, GitLab automatically assume that this merge request is to close issue number 1. And when this 
            merge request is merged into master, GitLab will automatically close issue #1 for use. Read more about 
            [Closing issues automatically](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically).
                 
               - Pick label(s): `mr::need reviews` and may be the frameworks/tool related to this merge request, e.g., Spring Boot, MongoDB, React etc., 
               - NOTE: DO NOT select "Squash commits when merge request is accepted", this will condense your commit 
                 history, thus negatively affect how they mark your contribution.
               - Whether to keep or delete your branch after the merge is entirely up to you. 
            - Submit your merge request and wait for the reviews to come in! 
   
# Workflow for adding/modify the documentation 

- Again, do not modify master directly.  
- Do as follow: 
    - checkout documentation branch, pull most recent changes (since we all share this branch)
    - working your documenation on the documentation branch (edit, commit, push)
    - once done, checkout master, pull most recent changes -> checkout documenation branch, merge master into it, there should be no conflicts involving fontent and backend code
    - merge documentation branch back to master, you can do this in 2 way
        - if you don't need any feeback on your doc, do the merge locally 

**May be we can make an exception on the rule of not modify master directly if you just work on the documentation folder? Or maybe from now on, we never merge documentation branch back to master again?**