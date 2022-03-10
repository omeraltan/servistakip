import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import SorunTemelUpdatePage from './sorun-temel-update.page-object';

const expect = chai.expect;
export class SorunTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.sorunTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-sorunTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class SorunTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('sorun-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('sorun-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateSorunTemel() {
    await this.createButton.click();
    return new SorunTemelUpdatePage();
  }

  async deleteSorunTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const sorunTemelDeleteDialog = new SorunTemelDeleteDialog();
    await waitUntilDisplayed(sorunTemelDeleteDialog.deleteModal);
    expect(await sorunTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.sorunTemel.delete.question/);
    await sorunTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(sorunTemelDeleteDialog.deleteModal);

    expect(await isVisible(sorunTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
