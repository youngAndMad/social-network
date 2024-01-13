package service

func getFileExtension(filename string) string {
	dotIndex := len(filename) - 1
	for i := len(filename) - 1; i >= 0; i-- {
		if filename[i] == '.' {
			dotIndex = i
			break
		}
	}
	return filename[dotIndex+1:]
}
