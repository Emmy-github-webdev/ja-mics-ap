#

### .github/workflows/

1. feature-ci.yml

Responsibilities

- Compile
- Unit Tests
- Integration Tests
- Docker Build Validation
- Gitleaks
- CodeQL

2. pr-validation.yml

Responsibilities

- Unit Tests
- Integration Tests
- Coverage
- SonarQube
- Dependency Scan
- Quality Gates

3. release-dev.yml

Responsibilities

- Build JAR
- Build Image
- Trivy
- Push ECR
- Create GitOps Dev PR

4. promote-staging.yml

Responsibilities

Read image from Dev
Update Staging Overlay
Create GitOps Staging PR
Deploy Staging
Run Validation Tests

promote-prod.yml