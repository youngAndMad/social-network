FROM golang:1.21-alpine

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

RUN go get github.com/bytedance/sonic@v1.9.1
RUN go mod tidy

COPY . .
RUN go build -x -o main ./cmd

EXPOSE 7070
CMD ["./main"]
