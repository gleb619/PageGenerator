package org.test.util


class Initiializer {

	FileUtil fileAdapter
	LogUtil logUtil = new LogUtil(Initiializer.class)
	def filesMap = [:]
	def filesList = []
	String pathOriginal
	String pathSource
	
	public Initiializer(FileUtil fileUtil, String pPath) {
		super();
		this.fileAdapter = fileUtil;
		this.pathOriginal = pPath;
		this.pathSource = pPath + "_";
		
		fileAdapter.mkDirIfNeeded(pathOriginal)
	}
	
	def init(List inAllows = null, Boolean debug = true) {
		String message = "init#"
		try {
			def allows = [new File(pathOriginal).name]
			if(inAllows != null){
				allows += inAllows
			}
			message +="\n${pathSource} {"
			message +="\n\ttry to clear workspace"
			clearWorkFlow(allows)
			fileAdapter.recreateDir(pathSource)
			fileAdapter = new FileUtil(pathOriginal)
			message +="\n\tinit, next one: recreating, after we continue..."
			fileAdapter.withEachAllFile {
				def myReg = /(\/.*)\/.*/
				def myMatcher = (it.getAbsolutePath().replace("\\", "/").replaceAll(pathOriginal, '') =~ myReg)
				if(myMatcher.matches()){
					fileAdapter.mkDirs(pathOriginal + myMatcher[0][1], '\t')
					fileAdapter.copy(it.getAbsolutePath(), pathSource + myMatcher[0][1] + '/' + it.name, '\t')
				}
				else{
					fileAdapter.copy(it.getAbsolutePath(), pathSource + '/' + it.name, '\t')
				}
			}
			message +="\n}"
		} catch (Exception e) {
			e.printStackTrace()
			message +="\n${e.getMessage()}"
			message +="\n-" * 100
			throw new RuntimeException("Wrong pPath: " + pathOriginal + ", change it")
		}
		
		logUtil.log(message)
	}

	def clearWorkFlow(def allows, String logLevel = "\t") {
		String message = ""
		fileAdapter = new FileUtil(new File(pathSource).getParent())
		message += "\n${logLevel}clearWorkFlow {\n${logLevel}\tparent is: ${new File(pathSource).parent}"
		fileAdapter.withEachAllDir {
			if(!allows.contains(it.name)){
				message += "\n${logLevel}\tdelete object: $it.name"
				if (it.isDirectory()) {
					fileAdapter.deleteDir(it.getAbsolutePath(), "\t\t")
				}
				else{
					fileAdapter.deleteFile(it.getAbsolutePath(), "\t\t")
				}
			}
		}
		
		message += "\n${logLevel}}"
		logUtil.log(message)
	}
	
}
