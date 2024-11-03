package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

class TestMapStructWithTaskBeanAndTaskDTO
{
//	@Test void testStandaloneTaskBean()
//	{
//		TaskBean  taskBean        = createTask(createTaskGroup());
//
//		// mapping
//		TaskDTO taskDTO = taskBean.toTarget();
//
//		assertIs(taskDTO, taskBean);
//	}
//
////	@Test void testStandaloneTaskDTOTwoWayMapping()
////	{
////		TaskDTO  taskDTO  = createTask(createTaskGroup());
////
////		// two way mapping
////		TaskBean taskBean = taskDTO .toTarget();
////		TaskDTO  taskDTO2 = taskBean.toSource();
////
////		assertIs(taskDTO2, taskBean);
////	}
//
//	private void assertIs(TaskDTO taskDTO, TaskBean taskBean)
//	{
//		assertThat(taskDTO.name           (), is(taskBean.name           ()));
//		assertThat(taskDTO.description    (), is(taskBean.description    ()));
//		assertThat(taskDTO.startEstimated (), is(taskBean.startEstimated ()));
//		assertThat(taskDTO.finishEstimated(), is(taskBean.finishEstimated()));
//		assertThat(taskDTO.startActual    (), is(taskBean.startActual    ()));
//		assertThat(taskDTO.finishActual   (), is(taskBean.finishActual   ()));
//		assertThat(taskDTO.effortEstimated(), is(taskBean.effortEstimated()));
//		assertThat(taskDTO.effortActual   (), is(taskBean.effortActual   ()));
//	}
//
//	private TaskBean createTask(TaskGroupBean taskGroup)
//	{
////		log.debug("taskGroupDTO\n{}", taskGroup);
//		TaskBean result = new TaskBean(taskGroup, "task name");
//		result
//				.description    ("description")
//				.startEstimated (LocalDate.now())
//				.startActual    (LocalDate.now())
//				.finishEstimated(LocalDate.now())
//				.finishActual   (LocalDate.now())
//				.effortEstimated(Duration.ZERO)
//				.effortActual   (Duration.ZERO);
////		log.debug("taskDTO\n{}", taskDTO);
//		return result;
//	}
//
//	private TaskGroupBean createTaskGroup() { return new TaskGroupBean("task group name"); }
}
