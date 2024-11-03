# description of datamodel in layered architecture

## common

## server

### jpa

### dto

## client

<div hidden>
```
@startuml

skinparam linetype ortho

package jpa.core
{
    interface Entity
}

package common
{
    interface TaskGroup
    interface Task
    TaskGroup "1" -r- "*" Task
}

package common.jpadto
{
    interface TaskEntity
    interface TaskDTO

    TaskDTO -[hidden]r- TaskEntity

    TaskEntity -u-|> Task
    TaskEntity -u-|> Entity
    TaskDTO    -u-|> Task
    TaskDTO    -u-|> Entity
}

common.jpa -[hidden]r- common.dto

package common.jpa
{
    TaskEntityJPA -u-|> TaskEntity
}

package common.dto
{
    TaskEntityDTO -u-|> TaskDTO
}

@enduml
```
</div>
